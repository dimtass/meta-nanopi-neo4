PROVIDES_remove = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/libwayland-egl"

USE_WL = "${@bb.utils.contains("DISTRO_FEATURES", "wayland", "yes", "no", d)}"

do_install_append () {
    # rm -f ${D}/${libdir}/libEGL*
    # rm -f ${D}/${libdir}/libGLESv1_CM.*
    # rm -f ${D}/${libdir}/libGLESv2.*
    # rm -f ${D}/${libdir}/libgbm*
    # rm -f ${D}/${libdir}/libwayland-egl*

    rm -f ${D}/${libdir}/libEGL.so
	rm -f ${D}/${libdir}/libEGL.so.1
	rm -f ${D}/${libdir}/libGLESv1_CM.so
	rm -f ${D}/${libdir}/libGLESv1_CM.so.1
	rm -f ${D}/${libdir}/libGLESv2.so
	rm -f ${D}/${libdir}/libGLESv2.so.2
	rm -f ${D}/${libdir}/libOpenCL.so
	rm -f ${D}/${libdir}/libOpenCL.so.1
	rm -f ${D}/${libdir}/libgbm.so
	rm -f ${D}/${libdir}/libgbm.so.1

	if [ "${USE_WL}" = "yes" ]; then
		rm -f ${D}/${libdir}/libwayland-egl.so
		rm -f libMali.so ${D}/${libdir}/libwayland-egl.so.1
    fi
}
