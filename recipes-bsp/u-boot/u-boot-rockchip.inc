DESCRIPTION="Upstream's U-boot configured for allwinner devices"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

LICENSE = "GPLv2+"

require recipes-bsp/u-boot/u-boot.inc

DEPENDS = "bison-native dtc-native bc-native swig-native atf-rk"

PROVIDES = "virtual/bootloader"

DEFAULT_PREFERENCE_rk3399="1"

SRC_URI = "git://git.denx.de/u-boot.git;branch=master \
            file://boot-rockchip.cmd \
            file://do_patch.sh \
            file://u-boot-rk3399 \
            file://rkEnv.txt \
"

do_compile_rk3399[depends] += "atf-rk:do_deploy"

UBOOT_SCRIPT = "boot.scr"
UBOOT_BIN = "rk3399-uboot.bin"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" V=1'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE += 'PYTHON=nativepython STAGING_INCDIR=${STAGING_INCDIR_NATIVE} STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'
EXTRA_OEMAKE += ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}" '

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(rk3399)"