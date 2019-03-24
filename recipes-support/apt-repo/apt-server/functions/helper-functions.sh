# Helper functions for deb-cmake-template
# Author: Dimitris Tassopoulos

usage ()
{
    cat << EOF
Cmake template to create DEB files
Usage: [variables] ./create_deb.sh

  Variables:
    VERSION_PACKAGE     - Cmake package version
    ARCHITECTURE        - DEB package target architecture
    BUILD_TYPE          - debug or release
    PARALLEL            - Number of CPUs to use (default is all)
    VERSION_PRE         - Pre-release version
    NO_GIT_HASH         - Add git hash to package name [true/false]
                            default is false
    NO_GIT_BRANCH       - Add git branch to package name [true/false]
                            default is false
EOF
    exit 1
}

pr_intro ()
{
    echo -e "${COLOR_CYAN}"
    echo -e "-----------------------------------------------"
    echo -e ">>>        CMake DEB package creator        <<<"
    echo -e "-----------------------------------------------"
    echo -e "report bugs to:                                "
    echo -e "dimitris.tassopoulos@native-instruments.de     "
    echo -e "-----------------------------------------------"
    echo -e "${COLOR_RESET}"
}

pr_msg ()
{
    echo -e "$1"
}

pr_info ()
{
    echo -e "${COLOR_YELLOW}>>> $1 ${COLOR_RESET}"
}

pr_error ()
{
    echo -e "${COLOR_BRED}>>> $1 ${COLOR_RESET}"
}

cleanup ()
{
    pr_info "Cleaning up"
    
}

exit_clean ()
{
    cleanup
    exit 1
}
