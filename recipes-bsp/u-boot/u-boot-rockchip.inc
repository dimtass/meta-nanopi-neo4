DESCRIPTION="Upstream's U-boot configured for rockchip devices"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

require u-boot-common.inc

LIC_FILES_CHKSUM ?= "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

SRC_URI += " \
            file://${SOC_FAMILY}-boot/boot.cmd \
            file://rkEnv.txt \
"

UBOOT_ENV_SUFFIX = "scr"
UBOOT_ENV = "boot"

do_configure() {
    cd ${S}
    ${WORKDIR}/armbian-patcher.sh ${WORKDIR}/patches-${SOC_FAMILY}-${UBOOT_VERSION}
    oe_runmake mrproper
    oe_runmake ${UBOOT_MACHINE}
}

do_compile() {
    set -x
    cd ${S}
    oe_runmake

    cp ${WORKDIR}/${SOC_FAMILY}-boot/boot.cmd ${WORKDIR}/boot.cmd
    ${B}/tools/mkimage -C none -A arm -T script -d ${WORKDIR}/boot.cmd ${WORKDIR}/${UBOOT_ENV_BINARY}

    # Add the soc specific parameters in the environment
    sed -e "s,overlay_prefix=,overlay_prefix=${OVERLAY_PREFIX},g" \
        -i ${WORKDIR}/rkEnv.txt
    sed -e "s,overlays=,overlays=${DEFAULT_OVERLAYS} ,g" \
        -i ${WORKDIR}/rkEnv.txt
    # echo "overlay_prefix=${OVERLAY_PREFIX}" >> ${WORKDIR}/rkEnv.txt
    # echo "overlays=${DEFAULT_OVERLAYS}" >> ${WORKDIR}/rkEnv.txt
}

do_install_append() {
    # Install files to rootfs/boot/
    install -m 644 ${WORKDIR}/rkEnv.txt ${D}/boot/rkEnv.txt

    # Fix broken device tree reference build into u-boot
    for dtb in ${KERNEL_DEVICETREE}; do
        dtb_base_name=`basename $dtb`
        dtb_dir_name=`dirname $dtb`
        if [ "${dtb_dir_name}" != "." ]; then
            install -d ${D}/boot/$dtb_dir_name
            ln -rsf ${D}/boot/$dtb_base_name ${D}/boot/$dtb
        fi
    done

    # Cleanup u-boot rootfs files
    rm -rf ${D}/boot/${SPL_BINARYNAME} ${D}/boot/${SPL_IMAGE} ${D}/boot/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}
}

do_deploy_append() {
    install -m 644 ${B}/${SPL_BINARY} ${DEPLOYDIR}/u-boot-${MACHINE}.bin
    install -m 644 ${WORKDIR}/rkEnv.txt ${DEPLOYDIR}/rkEnv.txt
}
