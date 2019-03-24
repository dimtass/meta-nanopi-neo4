#!/bin/bash -e

# Serve locally build .deb packages with a simple python web server. The web
# folder can be watched for changes and then the debian Package.gz file can
# be regenerated.
#
# Install the package called inotify-tools if you want this, set WATCH=false if not

# Default values
: ${ARCH:=arm}
: ${PORT:=8000}
: ${FOLDER_PREFIX:=www}
: ${REPO:=yocto}
: ${WATCH:=true}            # use inotifywait to watch for changes?
# this extracts the first ip in the list, might not be the one you want. Override it then.
: ${IP:=$(ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p' | head -n 1)}

source functions/bash_colors.sh
source functions/helper-functions.sh

function check_inotify
{
    if [[ ${WATCH} == true ]]; then
        set +e
        
        # check if command exist
        command -v inotifywait >/dev/null 2>&1
        
        if [ $? -ne 0 ]; then
            echo >&2 "ERROR: You have requested to watch the folder for changes but 'inotifywait' is not"
            echo >&2 "installed. Install the package called inotify-tools if you want this or set WATCH=false"
            echo >&2 "to disable this feature. You then have to re-run the script every time you add or change files."
            exit 1
        fi
    fi
}

function exit_cleanup
{
    echo ""
    echo "Caught ctrl-c. Shutting down python web server with PID: ${PID_WEBSERVER}"
    kill -SIGHUP ${PID_WEBSERVER}
    exit 0
}

# Get the options
while getopts "a:r:i:p:h:w:" OPTION
do
    case $OPTION in
    a) ARCH=${OPTARG} ;;
    r) REPO=${OPTARG} ;;
    i) IP=${OPTARG} ;;
    p) PORT=${OPTARG} ;;
    w) WATCH=${OPTARG} ;;
    h) usage ;;
    *) usage ;;
esac
done

pr_intro

pr_info "ARCH:  ${ARCH}"
pr_info "REPO:  ${REPO}"
pr_info "IP:    ${IP}"
pr_info "PORT:  ${PORT}"
pr_info "WATCH: ${WATCH}"

check_inotify

# create folder if it does not already exist
FOLDER_NAME=${FOLDER_PREFIX}-${ARCH}
pr_info "APT folder: ${FOLDER_NAME}"
mkdir -p ${FOLDER_NAME}

echo ""
echo "###################################################################################"
echo "To make sure that your packages are preferred over the ones"
echo "in apt.native-instrument.de, copy-paste this in a bash session on target:"
echo ""
echo "-------------------------- start copy -------------------------"
echo "cat > /etc/apt/sources.list.d/${REPO}.list << 'EOF'"
echo "deb [arch=${ARCH} trusted=yes] http://${IP}:${PORT} ${FOLDER_NAME}/"
echo "EOF"
echo ""
echo "cat > /etc/apt/preferences.d/${REPO}.pref << 'EOF'"
echo "Explanation: local development should always be preferred"
echo "Package: *"
echo "Pin: origin ${IP}"
echo "Pin-Priority: 1010"
echo "EOF"
echo "-------------------------- stop copy --------------------------"
echo ""
echo "###################################################################################"
echo ""

# catch ctrl-c
trap exit_cleanup SIGHUP SIGINT SIGTERM

# Run the web server in the background 
python -m SimpleHTTPServer ${PORT} &
PID_WEBSERVER=$!

# Create a debian "trivial" repo. Use a do-while structure because
# we need to run the dpkg-scanpackages at least once
while
    dpkg-scanpackages -m ${FOLDER_NAME} /dev/null | gzip -9c >${FOLDER_NAME}/Packages.gz
    
    # watch folder for changes?
    if [[ ${WATCH} == true ]]; then
        echo "watching..."
        ls ${FOLDER_NAME}
        inotifywait \
            --quiet \
            --exclude "Packages.gz" \
            --event create \
            --event delete \
            --event modify \
            --event attrib \
            --event move \
            ${FOLDER_NAME}
            
        # wait a little bit for things to settle down if not all files have been copied
        sleep 1
    else
        # Do nothing forever, until ctrl-c is pressed
        tail -f /dev/null
    fi
do
    :
done

# We will never end up down here
