require conf/distro/poky.conf

DISTRO = "rk-base"
DISTRO_NAME = "Rockchip Distro Base"
SDK_VENDOR = "-rksdk"

TARGET_VENDOR = "-rk"

XSERVER ?= "xserver-xorg \
		   xf86-input-libinput \
		   xf86-video-modesetting"

LICENSE_FLAGS_WHITELIST = "commercial license"

DISTRO_FEATURES += " \
		alsa \
		usbgadget \
		usbhost \
		bluez5 \
		systemd \
		x11 \
		wayland \
		"