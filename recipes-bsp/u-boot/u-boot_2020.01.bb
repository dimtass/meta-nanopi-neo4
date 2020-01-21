DESCRIPTION="Upstream's U-boot configured for nanopi-neo4 devices"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

require u-boot-rockchip.inc

UBOOT_VERSION = "2020.01"

SRCREV = "0b0c6af38738f2c132cfd41a240889acaa031c8f"
PV = "v${UBOOT_VERSION}+git${SRCPV}"

B = "${S}"