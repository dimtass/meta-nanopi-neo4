
MALI_FBDEV_rk3399 = "libmali-midgard-t86x-r14p0-r0p0-fbdev.so"
SRC_URI[libmali-midgard-t86x-r14p0-r0p0-fbdev.so.md5sum] = "06f3c7230f7e530797f5e0f7da188e28"

MALI_X11_rk3399 = "libmali-midgard-t86x-r14p0-r0p0-x11-gbm.so"
SRC_URI[libmali-midgard-t86x-r14p0-r0p0-x11-gbm.so.md5sum] = "726d93a658ff8c0fd0488372b9271827"

MALI_WAYLAND_rk3399 = "libmali-midgard-t86x-r14p0-r0p0-wayland-gbm.so"
SRC_URI[libmali-midgard-t86x-r14p0-r0p0-wayland-gbm.so.md5sum] = "ca22dcf663199082161efb3bbc7c63ee"

MALI_GBM_rk3399 = "libmali-midgard-t86x-r14p0-r0p0-gbm.so"
SRC_URI[libmali-midgard-t86x-r14p0-r0p0-gbm.s] = "da5046f93b40f3c9eafc1a88d8924153"

SRC_URI = "https://github.com/rockchip-linux/libmali/raw/rockchip/lib/${MALI_TUNE}/${MALI_NAME};name=${MALI_NAME}"
