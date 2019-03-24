DESCRIPTION = "A image used for test and benchmark."

require rk-image-multimedia.bb

EXTRA_IMAGE_FEATURE += "dev-pkgs dbg-pkgs"

AUTO_TEST_INSTALL = "\
	v4l-utils \
	glmark2 \
	cpufrequtils \
	usbutils \
	memtester \
	stress \
	libdrm-tests \
"

OTHERS_TEST_INSTALL = "\
    ${@bb.utils.contains("DISTRO_FEATURES", "x11 wayland", "", \
       bb.utils.contains("DISTRO_FEATURES",     "wayland", "", \
       bb.utils.contains("DISTRO_FEATURES",         "x11", "gtkperf", \
                                                           "", d), d), d)} \
"

# autotest
IMAGE_INSTALL += " \
	openssh \
	sshfs-fuse \
	dhcp-client \
	${AUTO_TEST_INSTALL} \
	${KERNEL_EXTRA_INSTALL_PKGS} \
	${SENSORS_PKGS} \
    ${APT_PKGS} \
    ${PYTHON2_PKGS} \
    ${PYTHON3_PKGS} \
    ${BMAPTOOL_PKGS} \
	packagegroup-core-tools-testapps \
	packagegroup-core-tools-profile \
	packagegroup-core-tools-debug \
	packagegroup-core-buildessential \
    packagegroup-extra-base \
    packagegroup-extra-buildessential \
    packagegroup-extra-debug \
    packagegroup-extra-testing \
    packagegroup-extra-qt5-sdk \
"
