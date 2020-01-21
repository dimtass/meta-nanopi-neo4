# Copyright (C) 2017 Jacob Chen
# Released under the MIT license (see COPYING.MIT for the terms)

SOC_FAMILY = "rk3399"

require conf/machine/include/arm/arch-armv8a.inc
require conf/machine/include/soc-family.inc

RK_MALI_LIB := "libmali-midgard-t86x-r14p0${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '-wayland', bb.utils.contains('DISTRO_FEATURES', 'x11', '', '-gbm', d), d)}.so"

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
MACHINE_EXTRA_RRECOMMENDS += " \
        kernel-modules \
	    ${RK_WIFIBT_FIRMWARES} \
"

MACHINEOVERRIDES .= ":rockchip"


# Custom kernel might not support newest python
PACKAGECONFIG_remove_pn-perf += " scripting"

PREFERRED_PROVIDER_virtual/egl ?= "rockchip-libmali"
PREFERRED_PROVIDER_virtual/libgles1 ?= "rockchip-libmali"
PREFERRED_PROVIDER_virtual/libgles2 ?= "rockchip-libmali"
PREFERRED_PROVIDER_virtual/libgles3 ?= "rockchip-libmali"
PREFERRED_PROVIDER_virtual/libopencl ?= "rockchip-libmali"
PREFERRED_PROVIDER_virtual/libgbm ?= "rockchip-libmali"
PACKAGECONFIG_pn-wayland = "${@ 'no-egl' if 'wayland' in d.getVar('RK_MALI_LIB') else '' }"

# HACK: Use libmali's KHR headers
PACKAGECONFIG_append_pn-mesa-gl += " no-khr-headers"
DEPENDS_append_pn-mesa-gl += " rockchip-libmali"

PREFERRED_PROVIDER_virtual/libgl ?= "mesa-gl"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa-gl"

DEFAULTTUNE_rk3399 = "aarch64"