DESCRIPTION = "ARM Trusted Firmware for rk3399"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

SECTION = "BSP"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

inherit pkgconfig deploy
include conf/include/layer-vars.inc

PV = "1.3+git${SRCPV}"

DEPENDS = "gcc-arm-none-eabi-native rkbin-native"

GCC_ARM_NONE_TOOLCHAIN  ?= "${STAGING_DIR_NATIVE}${bindir}/arm-none-eabi"
RKBIN_TOOLS ?= "${STAGING_DIR_NATIVE}${bindir}/rkbin"

SRC_URI = " \
        git://github.com/ayufan-rock64/arm-trusted-firmware.git;branch=rockchip \
        file://add-trust-ini.patch \
        "

SRCREV = "f947c7e05a34db0c5b908a5347184fcaa9a32d95"

PLAT_rk3399 = "rk3399"

S = "${WORKDIR}/git"

do_compile () {
    set -x
    export CROSS_COMPILE="${TARGET_PREFIX}"
    export M0_CROSS_COMPILE="${GCC_ARM_NONE_TOOLCHAIN}/bin/arm-none-eabi-"
    cd ${S}
    # Clear LDFLAGS to avoid the option -Wl recognize issue
    unset LDFLAGS

    echo "-> Build ${PLAT} bl31.bin"
    # Set BUIL_STRING with the revision info

    CFLAGS=-Wno-error oe_runmake clean PLAT=${PLAT}
    CFLAGS=-Wno-error oe_runmake PLAT=${PLAT} DEBUG=1 bl31

    # Create trust.bin
    trust_merger trust.ini

    # This bin is 4GB! Delete it to save some space
    rm ${S}/build/${PLAT}/debug/bl31.bin

    unset CROSS_COMPILE
    unset M0_CROSS_COMPILE
}

do_deploy () {
    install -d ${DEPLOYDIR}/${BOOT_TOOLS_DIR}
    install -m 0644 ${S}/trust.bin ${DEPLOYDIR}/trust.bin
}

addtask deploy after do_compile
