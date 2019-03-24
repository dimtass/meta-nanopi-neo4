# This file exports the various paths that are used for the image build


# These are needed for the rkbin_git.bb
BOOT_TOOLS_DIR = "boot-rk3399"

# GCC arm-none-eabi paths
export GCC_ARM_NONE_TOOLCHAIN_RECIPE = "${bindir}/arm-none-eabi"
export GCC_ARM_NONE_TOOLCHAIN  = "${STAGING_DIR_NATIVE}${GCC_ARM_NONE_TOOLCHAIN_RECIPE}"

# RKBIN paths
export RKBIN_TOOLS_RECIPE = "${bindir}/rkbin"
export RKBIN_TOOLS = "${STAGING_DIR_NATIVE}${RKBIN_TOOLS_RECIPE}"
