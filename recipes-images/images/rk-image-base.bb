DESCRIPTION = "Nanopi-neo4 base Image. Note that this is a demo image and debug=tweaks \
				are enabled. When used for anything than evaluation and testing and you \
				are based on this image, then you need to remove this."
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"
LICENSE = "MIT"

inherit core-image
require recipes-images/packagegroup/packagegroups.inc

do_image_wic[depends] += " \
        gptfdisk-native:do_populate_sysroot \
        mtools-native:do_populate_sysroot \
        dosfstools-native:do_populate_sysroot \
        "
# Build wic image after sunxi_sdimg, because we need the 'boot/img' file
do_image_wic[recrdeptask] += "do_image_wksbuild"
do_image_wksbuild[recrdeptask] += "do_image_rkboot"

IMAGE_FEATURES += "\
	debug-tweaks \
	package-management \
	${@bb.utils.contains("DISTRO_FEATURES", "x11 wayland", "", \
	   bb.utils.contains("DISTRO_FEATURES", "x11", "x11-base", "", d), d)} \
"

IMAGE_INSTALL += " \
	packagegroup-core-ssh-openssh \
	packagegroup-base \
	${@bb.utils.contains("DISTRO_FEATURES", "x11 wayland", "xserver-xorg-xwayland weston-xwayland", "", d)} \
	${@bb.utils.contains("DISTRO_FEATURES", "wayland", "weston weston-init weston-examples weston-ini", "", d)} \
"

