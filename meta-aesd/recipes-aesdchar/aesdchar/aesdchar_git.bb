inherit logging
inherit update-rc.d 

# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-xcoponet.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
# set to reference a specific commit hash in your assignment repo
SRCREV = "094e31d4d5108b7ff4157835f83f5cb4afb367f8"

SRC_URI += "file://init_aesdchar.sh"


# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/aesd-char-driver"

# TODO: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar.ko"


inherit module

FILES:${PN} += "${sysconfdir}/init.d/aesdchar"


INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_configure () {
	:
}

do_install() {
    bbwarn "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/"

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init_aesdchar.sh ${D}${sysconfdir}/init.d/aesdchar


    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${B}/aesdchar.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/aesdchar_load ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/aesdchar_unload ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
}

FILES_${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar.ko"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_load"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_unload"

RDEPENDS_${PN} = "kernel-module-aesdchar"



# INITSCRIPT_PACKAGES = "${PN}"
# INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

# do_configure () {
# 	:
# }

# do_compile () {
# 	oe_runmake
# }

# do_install () {
# 	bbwarn "D ${D}"
# 	bbwarn "S ${S}"
#     # TODO: Install your binaries/scripts here.
# 	# Be sure to install the target directory with install -d first
# 	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
# 	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
# 	# and
# 	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
# 	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
#     install -d ${D}${bindir}
# 	install -m 0755 ${S}/aesdsocket ${D}${bindir}/

#     # Install the init script
#     install -d ${D}${sysconfdir}/init.d
#     install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/
# }
