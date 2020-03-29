AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"
SECTION = "kernel"
LICENSE = "GPLv2"
COMPATIBLE_MACHINE = "(rk3399)"

require recipes-kernel/linux/linux-yocto.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# Pull in the devicetree files into the rootfs
#RDEPENDS_${KERNEL_PACKAGE_NAME}-base += "kernel-devicetree"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

LINUX_VERSION = "4.4"

S = "${WORKDIR}/git"

PV = "4.4.179"
SRCREV = "31f28f9123ba30f4e794794fe915ced7e9e3bcec"

# defconfig from armbian/build/config/kernel/linux-rk3399-default.config
SRC_URI = " \
        git://github.com/dimtass/kernel-rockchip.git;branch=nanopi4-linux-v4.4.y \
        file://do_patch.sh \
        file://rk3399-default \
        file://defconfig \
"

do_patch_append() {
    cd ${WORKDIR}/git
    ${WORKDIR}/do_patch.sh ${WORKDIR}/rk3399-default
}
