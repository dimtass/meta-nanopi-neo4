DESCRIPTION = "Baremetal GCC for ARM"
LICENSE = "GPL-3.0-with-GCC-exception & GPLv3"

LIC_FILES_CHKSUM = "file://share/doc/gcc-arm-none-eabi/license.txt;md5=f77466c63f5787f4bd669c402aabe061"

inherit native deploy

SRC_URI = "https://developer.arm.com/-/media/Files/downloads/gnu-rm/7-2017q4/gcc-arm-none-eabi-7-2017-q4-major-linux.tar.bz2;name=gcc-arm-none"

SRC_URI[gcc-arm-none.md5sum] = "d3b00ae09e847747ef11316a8b04989a"
SRC_URI[gcc-arm-none.sha256sum] = "96a029e2ae130a1210eaa69e309ea40463028eab18ba19c1086e4c2dafe69a6a"

GCC_ARM_NONE_TOOLCHAIN_RECIPE = "${bindir}/arm-none-eabi"
GCC_ARM_NONE_TOOLCHAIN  = "${STAGING_DIR_NATIVE}${GCC_ARM_NONE_TOOLCHAIN_RECIPE}"

S = "${WORKDIR}/gcc-arm-none-eabi-7-2017-q4-major"

# only x86_64 is supported
COMPATIBLE_HOST = "x86_64.*-linux"
COMPATIBLE_HOST_class-target = "null"

do_install() {
	install -d ${D}/${GCC_ARM_NONE_TOOLCHAIN_RECIPE}
    cp -r ${S}/. ${D}/${GCC_ARM_NONE_TOOLCHAIN_RECIPE}
}

INSANE_SKIP_${PN} = "already-stripped"

BBCLASSEXTEND =+ "native nativesdk"

INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"