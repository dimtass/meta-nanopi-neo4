#!/bin/bash
: ${MACHINE:=nanopi-neo4}
: ${IMAGE:=testing}
: ${SD:=$1}
: ${IMAGE_FILE:=build/tmp/deploy/images/${MACHINE}/rk-image-${IMAGE}-${MACHINE}.wic.bz2}

if [ "$(whoami)" != "root" ]; then
    echo "You need to run the script as root..."
    exit 1
fi

if [ -z "${MACHINE}" ]; then
    cat <<EOF
You need to set which MACHINE image to flash to the SD. eg.:
$ sudo IMAGE=multimedia ./flash_sd.sh /dev/sdX
EOF
    exit 1
fi

umount ${SD}*
bmaptool copy ${IMAGE_FILE} ${SD}