DESCRIPTION = "rk binaries and tools (armbian branch)"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

LICENSE = "BINARY"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=564e729dd65db6f65f911ce0cd340cf9"
NO_GENERIC_LICENSE[BINARY] = "LICENSE.TXT"

inherit deploy
include conf/include/layer-vars.inc

SRC_URI = "git://github.com/armbian/rkbin.git;branch=master"
SRCREV = "a632039224934f6f4600d5bd3cd2b8d93f716f83"

RK_BIN_FOLDER ?= "rk33"
RKBIN_TOOLS_RECIPE ?= "${bindir}/rkbin"
RKBIN_TOOLS ?= "${STAGING_DIR_NATIVE}${RKBIN_TOOLS_RECIPE}"

RK_DDR_BIN ?= "rk3399_ddr_800MHz_v1.24.bin"
RK_MINILOADER_BIN ?= "rk3399_miniloader_v1.19.bin"

S = "${WORKDIR}/git"

do_install_class-native() {
    set -x
	# install -d ${D}/${RKBIN_TOOLS_RECIPE}
    # cp -r ${S}/. ${D}/${RKBIN_TOOLS_RECIPE}
	install -d ${D}/${bindir}
	install -m 0755 "${S}/tools/trust_merger" ${D}/${bindir}
	install -m 0755 "${S}/tools/firmwareMerger" ${D}/${bindir}

	install -m 0755 "${S}/tools/kernelimage" ${D}/${bindir}
	install -m 0755 "${S}/tools/loaderimage" ${D}/${bindir}

	install -m 0755 "${S}/tools/mkkrnlimg" ${D}/${bindir}
	install -m 0755 "${S}/tools/resource_tool" ${D}/${bindir}
}

do_deploy() {
    # install -d ${DEPLOYDIR}/${BOOT_TOOLS_DIR}
	[ -f "${DEPLOY_DIR_IMAGE}/rk-ddr.bin" ] && rm ${DEPLOY_DIR_IMAGE}/rk-ddr.bin
    install -m 0644 ${S}/${RK_BIN_FOLDER}/${RK_DDR_BIN} ${DEPLOYDIR}/rk-ddr.bin
	[ -f "${DEPLOY_DIR_IMAGE}/rk-miniloader.bin" ] && rm ${DEPLOY_DIR_IMAGE}/rk-miniloader.bin
    install -m 0644 ${S}/${RK_BIN_FOLDER}/${RK_MINILOADER_BIN} ${DEPLOYDIR}/rk-miniloader.bin
}

addtask deploy after do_unpack

BBCLASSEXTEND =+ "native nativesdk"

COMPATIBLE_MACHINE_class-target = "(rk3399)"

INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
