DESCRIPTION = "Adds the apt repo to the board. You need to setup an apt server to host the deb packages"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS_${PN} += "bash"

APT_SERVER_IP ?= "192.168.0.2"
APT_SERVER_PORT ?= "8000"
APT_REPO_NAME ?= "yocto"
APT_LIST ?= "${APT_REPO_NAME}.list"
APT_PREF ?= "${APT_REPO_NAME}.pref"
SRC_URI = "file://00_disable-cache"

create_files() {
    # clean up previous files
    [ -f ${WORKDIR}/${APT_LIST} ] && rm ${APT_LIST}
    [ -f ${WORKDIR}/${APT_PREF} ] && rm ${APT_PREF}

    # Create list file
    cat >> "${WORKDIR}/${APT_LIST}" <<EOF
deb [arch=${TUNE_ARCH} trusted=yes] http://${APT_SERVER_IP}:${APT_SERVER_PORT} yocto-${TUNE_ARCH}
EOF

    # Create the pref file
    cat >> "${WORKDIR}/${APT_PREF}" <<EOF
Explanation: remote apt server for yocto-${TUNE_ARCH}
Package: *
Pin: origin ${APT_SERVER_IP}
Pin-Priority: 1010
EOF
}

do_install () {

    create_files

    install -d ${D}${sysconfdir}/apt/sources.list.d/
    install -m 0644 ${WORKDIR}/${APT_LIST} ${D}${sysconfdir}/apt/sources.list.d/

    install -d ${D}${sysconfdir}/apt/preferences.d/
    install -m 0644 ${WORKDIR}/${APT_PREF} ${D}${sysconfdir}/apt/preferences.d/

    install -d ${D}${sysconfdir}/apt/apt.conf.d/
    install -m 0644 ${WORKDIR}/00_disable-cache ${D}${sysconfdir}/apt/apt.conf.d/
}
