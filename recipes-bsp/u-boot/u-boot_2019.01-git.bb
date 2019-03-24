DESCRIPTION="Upstream's U-boot configured for nanopi-neo4 devices"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

require u-boot-rockchip.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

SRCREV = "d3689267f92c5956e09cc7d1baa4700141662bff"
PV = "2019.01+git${SRCPV}"

OUTDIR = "u-boot-${MACHINE}"

do_configure_append() {
    cd ${S}
    ${WORKDIR}/do_patch.sh ${WORKDIR}/u-boot-rk3399
}

do_compile_append() {
    set -x
    cd ${B}
    cp ${WORKDIR}/boot-rockchip.cmd boot.cmd
    ${B}/tools/mkimage -C none -A arm -T script -d boot.cmd ${UBOOT_SCRIPT}
}

do_deploy() {
    # Copy results to boot dir
    install -d ${DEPLOYDIR}/${OUTDIR}
    install -m 644 ${B}/${SPL_BINARY} ${DEPLOYDIR}/u-boot-${MACHINE}.bin
    install -m 644 ${B}/${UBOOT_SCRIPT} ${DEPLOYDIR}/${OUTDIR}/
    # Add the soc specific parameters in the environment
    echo "overlay_prefix=${OVERLAY_PREFIX}" >> ${WORKDIR}/rkEnv.txt
    echo "overlays=${DEFAULT_OVERLAYS}" >> ${WORKDIR}/rkEnv.txt
    install -m 644 ${WORKDIR}/rkEnv.txt ${DEPLOYDIR}/${OUTDIR}/rkEnv.txt
}

addtask deploy after do_compile

COMPATIBLE_MACHINE = "(rk3399)"