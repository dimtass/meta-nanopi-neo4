Yocto meta layer for NanoPi-Neo4.
----

This meta layer expands the [meta-rockchip](https://github.com/rockchip-linux/meta-rockchip)
layer and adds the [armbian](https://github.com/armbian/build) BSP
for the NanoPi-neo4. The layer also supports libmali for 3D acceleration.

There's a blog post [here](https://www.stupid-projects.com/nanopi-neo4-yocto-meta-layer/)
with some more info about this layer.

> Update: This meta layer is not updated often, therefore there might be a case that
your build is broken. In this case you should use the proposed hashes for each repo,
because those were the ones that the layer was tested.

## Distros
The available supported distros are
* `rk-none`: For no graphics support (only console images)
* `rk-wayland`: For Wayland support (it uses Weston)
* `rk-w11`: For Xorg support
* `rk-xwayland`: For Xorg+Wayland support

## Images
The images that are supported are:
* `rk-image-base`: The base image with a few basic packages
* `rk-image-multimedia`: Includes libdrm and gstreamer
* `rk-image-qtwayland`: Supports Qt5 with qtwayland
* `rk-image-testing`: A full blown image with a lot of debugging and test tools

The testing image includes a lot of extra tools and includes them as `packagegroups`.
You can find and edit those packagegroups in `meta-nanopi-neo4/recipes-images/packagegroup`.

The images are located in `meta-nanopi-neo4/recipes-images/images` and you can use them
to create your own custom images (e.g. a Qt5 image that includes all the testing tools).

The image build results in a `wic.bz2` file and you can use the
[bmap-tools](https://github.com/intel/bmap-tools) to flash it like that:
```sh
bmaptool copy build/tmp/deploy/images/nanopi-neo4/rk-image-testing-nanopi-neo4.wic.bz2 /dev/sdX
```

> The great thing with bmap is that you can flash huge images in an SD card in a few
seconds (depending of course the amount of real binary data in the image).

You can control the size of rootfs by changing the value of the `ROOT_EXTRA_SPACE` in
`meta-nanopi-neo4/classes/nanopi-neo4-create-wks.bbclass`. You can override this value
either in this file or in your `local.conf` file.

By default there's a small swap partition in the image and you can control the size
with the `RK_SWAP_SIZE` parameter in `meta-nanopi-neo4/classes/nanopi-neo4-create-wks.bbclass`.
If you don't want to use swap the set this value to `0` like this:
```sh
RK_SWAP_SIZE = "0"
```

## Default `local.conf` and `bblayers.conf`
There's a script that prepares the build environment that uses a default `local.conf.sample`
and `bblayers.conf.sample` file that are located in `meta-nanopi-neo4/conf/`.

## Versions
Some useful component versions for this layer:
* `u-boot`  : 2020.01
* `Kernel`  : 5.4.13
* `libmali` : r14p0

## How to use this layer
This layer has some other layer dependencies. Create a new working folder and then in this
folder run these commands:

```sh
mkdir sources
cd sources
git clone --depth 1 -j 8 git@bitbucket.org:dimtass/meta-nanopi-neo4.git
git clone --depth 1 -j 8 -b zeus git@github.com:meta-qt5/meta-qt5.git
git clone --depth 1 -j 8 -b zeus git@github.com:openembedded/meta-openembedded.git
git clone --depth 1 -j 8 -b zeus git://git.yoctoproject.org/poky
git clone --depth 1 -j 8 -b zeus git@github.com:rockchip-linux/meta-rockchip.git
rm -rf meta-rockchip/recipes-bsp/u-boot
cd ..
ln -s sources/meta-nanopi-neo4/scripts/setup-environment.sh
```

> Note: The `meta-rockchip/recipes-bsp/u-boot` reipes they break this layer
because the bbappend ovewrites the u-boot recipe of the meta-nanopi-neo4.
Therefore, the solution is to remove the whole folder as it's not needed.

The commit hashes I've succesfully tested are:
```
meta-rockchip        = "yocto-next:5b8997a5992204cb773a55dca9406c0f95a4d061"
meta-qt5             = "zeus:a582fd4c810529e9af0c81700407b1955d1391d2" 
meta-openembedded     = "zeus:e855ecc6d35677e79780adc57b2552213c995731"     
meta-poky            = "zeus:cf92a2d567260b91a259652bad0ecd790750f710"
```

These commands will create a `sources/` folder and also a link to the top level folder
that points to the build prepare script. Then you need to call this script by passing
two arguments. One is the `MACHINE` and other the `DISTRO`. `MACHINE` is always
`nanopi-neo4` and `DISTRO` can be one of the supported ones of this layer (see: above).

Therefore, to build the image for the NanoPi-Neo4 and with `Wayland` support run this:
```sh
MACHINE=nanopi-neo4 DISTRO=rk-wayland source ./setup-environment.sh build
```

Then to build the `testing` image that includes a variety of testing, debugging and
development tools, run this:

```sh
bitbake rk-image-testing
```

## Benchmark
For the `rk-image-testing` and the rk-wayland `DISTRO`, you can use the `glmark2`
benchmark for testing the GPU. The image has two versions of the tool, the
`glmark2-es2-drm` and `glmark2-es2-wayland`. The first one you can just run it
from the uart console like this:
```sh
glmark2-es2-drm
```

This will also ouput the benchmark rendering on the screen. In case you want
to benchmark the raw GPU performance then you can run the benchmark and render
to an off-screen surface like this:
```sh
glmark2-es2-drm --off-screen
```

If you want to use the `glmark2-es2-wayland` then you need first to export the
`XDG_RUNTIME_DIR` and `WAYLAND_DISPLAY` and then run the benchmark, like this:
```sh
export XDG_RUNTIME_DIR=/run/user/1000
export WAYLAND_DISPLAY=wayland-0
glmark2-es2-wayland
```

## Author
Dimitris Tassopoulos <dimtass@gmail.com>