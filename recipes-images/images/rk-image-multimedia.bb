# Copyright (C) 2017 Fuzhou Rockchip Electronics Co., Ltd
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "A image with Rockchip's multimedia packages."

require rk-image-base.bb

IMAGE_INSTALL += " \
	libdrm-rockchip \
	packagegroup-rk-gstreamer-full \
	gstreamer1.0-libav \
"
