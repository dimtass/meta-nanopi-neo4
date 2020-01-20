DESCRIPTION = "Baremetal GCC for ARM"
LICENSE = "GPL-3.0-with-GCC-exception & GPLv3"

LIC_FILES_CHKSUM = "file://share/doc/gcc-arm-none-eabi/license.txt;md5=c18349634b740b7b95f2c2159af888f5"

inherit native deploy

SRC_URI = "https://developer.arm.com/-/media/Files/downloads/gnu-rm/8-2019q3/RC1.1/gcc-arm-none-eabi-8-2019-q3-update-linux.tar.bz2"
SRC_URI[md5sum] = "6341f11972dac8de185646d0fbd73bfc"

PROVIDES = "gcc-arm-none-eabi"

GCC_ARM_NONE_TOOLCHAIN_RECIPE = "${bindir}/arm-none-eabi"
GCC_ARM_NONE_TOOLCHAIN  = "${STAGING_DIR_NATIVE}${GCC_ARM_NONE_TOOLCHAIN_RECIPE}"

S = "${WORKDIR}/gcc-arm-none-eabi-8-2019-q3-update"

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