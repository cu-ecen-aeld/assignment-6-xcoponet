inherit update-rc.d 

# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-xcoponet.git;protocol=ssh;branch=master \
           file://0001-Modified-Makefile-to-include-necessary-changes-for-s.patch \
           "

SRC_URI += "file://init_modules.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "718cbdf07e082486e008537660e6b9fb4fb07a45"

S = "${WORKDIR}/git"

inherit module

FILES:${PN} += "${sysconfdir}/init.d/modules"


INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "modules"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_configure () {
	:
}

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init_modules.sh ${D}${sysconfdir}/init.d/modules

    # install -d ${D}${bindir}
	# install -m 0755 ${S}/misc-modules/module_load ${D}${bindir}/
	# install -m 0755 ${S}/misc-modules/module_unload ${D}${bindir}/

    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${B}/misc-modules/*.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/misc-modules/module_load ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/misc-modules/module_unload ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
}

FILES_${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/*.ko"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/module_load"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/module_unload"

# RDEPENDS_${PN} = "kernel-module-scull"
