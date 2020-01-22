B = "${S}"

do_configure_prepend() {
    cd ${STAGING_KERNEL_DIR}
    oe_runmake mrproper
}

do_configure() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake CC="${KERNEL_CC}" LD="${KERNEL_LD}" AR="${KERNEL_AR}" \
	           -C ${STAGING_KERNEL_DIR} O=${STAGING_KERNEL_BUILDDIR} scripts prepare
}