DESCRIPTION = "rk firmwares (armbian branch)"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

LICENSE = "BINARY"
LIC_FILES_CHKSUM = "file://README.md;md5=09b8df006ba894da0cc12878e6b9c834"
NO_GENERIC_LICENSE[BINARY] = "README.md"

inherit allarch

SRC_URI = "git://github.com/armbian/firmware.git;branch=master"
SRCREV = "66e243fec8acc68b6f6b8bbc5b9728c435187074"

S = "${WORKDIR}/git"

do_install () {
	install -d ${D}/lib/firmware/
	cp -rf ${S}/* ${D}/lib/firmware/
}

FILES_${PN} = "/lib/firmware/*"