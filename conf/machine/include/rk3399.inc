# Copyright (C) 2017 Jacob Chen
# Released under the MIT license (see COPYING.MIT for the terms)

SOC_FAMILY = "rk3399"

require conf/machine/include/arm/arch-armv8a.inc
require conf/machine/include/soc-family.inc

MACHINEOVERRIDES =. "mali-gpu:mali-midgard:"

PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"
PREFERRED_VERSION_u-boot ?= "2019.01%"

PREFERRED_PROVIDER_virtual/kernel ?= "friendlyarm-rockchip"
PREFERRED_VERSION_linux-rockchip ?= "4.4"

SERIAL_CONSOLES = "1500000;ttyS2"
KERNEL_IMAGETYPE = "Image"

SPL_BINARY = "u-boot-dtb.bin"

OVERLAY_PREFIX ?= "rockchip"
DEFAULT_OVERLAYS ?= ""

# Add the wks creation class. This will enable the custom
# wks file creation per image
IMAGE_FSTYPES += "ext4 rkboot wksbuild wic.bz2 wic.bmap"
IMAGE_CLASSES += "nanopi-neo4-boot-partition nanopi-neo4-create-wks"

# Install the kernel modules
MACHINE_EXTRA_RRECOMMENDS += "kernel-modules"