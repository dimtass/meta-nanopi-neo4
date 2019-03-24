## These are some package groups I'm using
# but are not enabled by default. If you
# want to enable them then add them in the
# IMAGE_INSTALL in allwinner-image.bb

# Sensors
SENSORS_PKGS = " \
    lmsensors-sensors \
    lmsensors-libsensors \
"

# Standard packages
STANDARD_PKGS = " \
	io \
    tar \
    bash \
    merge-files \
    wget \
    procps \
    usbreset \
    udev \
	udev-extraconf \
    u-boot-fw-tools \
    dfu-util \
"

APT_PKGS = " \
    apt \
    apt-repo \
"

# Python2.7 related packages
PYTHON2_PKGS = " \
    python \
    python-pip \
    python-modules \
    python-dbus \
"

# Python3 packages
PYTHON3_PKGS = " \
    python3 \
    python3-pip \
    python3-modules \
"

# Bmap tool used to flash images
BMAPTOOL_PKGS = " \
    gnupg \
    python3 \
    python3-modules \
    python3-six \
    bzip2 \
    bmap-tools-git \
"

WIFI_SUPPORT_PKGS = " \
    crda \
    iw \
    wireless-tools \
    wpa-supplicant \
    dnsmasq \
    hostapd \
"

# Packages for ALSA
ALSA_PKGS = " \
    alsa-tools \
    alsa-utils \
    alsa-server \
"

# Kernel packages
KERNEL_EXTRA_INSTALL_PKGS = " \
    kernel-dev \
    kernel-devsrc \
"