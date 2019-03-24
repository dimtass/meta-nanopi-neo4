DESCRIPTION = "Setup CPU for maximum performance"
AUTHOR = "Dimitris Tassopoulos <dimitris.tassopoulos@gmail.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

RDEPENDS_${PN} += "bash"

FILESEXTRAPATHS_append = "${THISDIR}/files:"

SRC_URI = " \
		file://rk-overclocking; \
		file://rk-overclocking.service \
"

S = "${WORKDIR}"

do_install () {
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/rk-overclocking.service ${D}${systemd_system_unitdir}

	install -d ${D}/usr/bin/
	install -m 0755 ${WORKDIR}/rk-overclocking ${D}/usr/bin/
}

FILES_${PN} += " \
		${systemd_unitdir}/system/rk-overclocking.service \
		/usr/bin/rk-overclocking \
"

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "rk-overclocking.service"
