DESCRIPTION="Upstream's U-boot configured for allwinner devices"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

require u-boot-fw-utils.inc

UBOOT_VERSION = "2020.01"

SRCREV = "0b0c6af38738f2c132cfd41a240889acaa031c8f"
PV = "v${UBOOT_VERSION}+git${SRCPV}"