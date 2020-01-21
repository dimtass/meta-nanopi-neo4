DESCRIPTION = "My Custom Package Groups"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

inherit packagegroup

require recipes-images/packagegroup/packagegroups.inc

PACKAGES = " \
    packagegroup-extra-base \
    packagegroup-extra-buildessential \
    packagegroup-extra-debug \
    packagegroup-extra-testing \
    packagegroup-extra-qt5-sdk \
    "

RDEPENDS_packagegroup-extra-buildessential = " \
    cmake \
    coreutils \
    gdb \
    gdbserver \
    diffutils \
    file \
    gettext \
    git \
    ldd \
    libtool \
    make \
    pkgconfig \
    ninja \
    valgrind \
    "

RDEPENDS_packagegroup-extra-debug = " \
    lttng-tools \
    bc \
    ethtool \
    htop \
    nano \
    pciutils \
    binutils \
    zip \
    unzip \
    util-linux \
    tmux \
    lsof \
    strace \
    "

RDEPENDS_packagegroup-extra-testing = "\
    rt-tests \
    stress \
    sysstat \
    perf \
    memtester \
    iperf3 \
    "

RDEPENDS_packagegroup-extra-base = " \
    rk-overclocking \
	armbian-firmware \
    default-modules \
    ${STANDARD_PKGS} \
    ${WIFI_SUPPORT_PKGS} \
	${@bb.utils.contains("DISTRO_FEATURES", "alsa", "${ALSA_PKGS}", "", d)} \
    "


RDEPENDS_packagegroup-extra-qt5-sdk = " \
"