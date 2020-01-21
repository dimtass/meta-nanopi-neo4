DESCRIPTION="Upstream's U-boot configured for allwinner devices"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"
LICENSE = "GPLv2+"

require recipes-bsp/u-boot/u-boot.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:${THISDIR}/../../scripts:"

DEPENDS += "bc-native dtc-native swig-native python3-native bison-native flex-native"
DEPENDS_append_rk3399 = " atf-rk "

do_compile_rk3399[depends] += "atf-rk:do_deploy"

COMPATIBLE_MACHINE = "(rk3399)"

DEFAULT_PREFERENCE = "1"

EXTRA_OEMAKE += ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}" '
EXTRA_OEMAKE_class-target = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${CC} ${CFLAGS} ${LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" V=1'
EXTRA_OEMAKE_class-cross = 'HOSTCC="${CC} ${CFLAGS} ${LDFLAGS}" V=1'

SRC_URI = " \
        git://gitlab.denx.de/u-boot.git;branch=master \
        file://armbian-patcher.sh \
        file://patches-${SOC_FAMILY}-${UBOOT_VERSION} \
    "

S = "${WORKDIR}/git"