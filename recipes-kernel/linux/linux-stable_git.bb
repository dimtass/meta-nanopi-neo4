AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"
SECTION = "kernel"
LICENSE = "GPLv2"
COMPATIBLE_MACHINE = "(rk3399)"

require linux-stable.inc

LINUX_VERSION = "5.4"
LINUX_VERSION_EXTENSION = "-nanopi-neo4"
PV = "5.4.13"
SRCREV = "ba19874032074ca5a3817ae82ebae27bd3343551"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable_${LINUX_VERSION}:${THISDIR}/../../scripts:"

# KERNEL_VERSION_SANITY_SKIP = "1"

# defconfig from armbian/build/config/kernel/linux-rk3399-default.config
SRC_URI = " \
        git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git \
        ${ARMBIAN_URI} \
"

B = "${S}"
