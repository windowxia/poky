SUMMARY = "ACPICA tools for the development and debug of ACPI tables"
DESCRIPTION = "The ACPI Component Architecture (ACPICA) project provides an \
OS-independent reference implementation of the Advanced Configuration and \
Power Interface Specification (ACPI). ACPICA code contains those portions of \
ACPI meant to be directly integrated into the host OS as a kernel-resident \
subsystem, and a small set of tools to assist in developing and debugging \
ACPI tables."

HOMEPAGE = "http://www.acpica.org/"
SECTION = "console/tools"

LICENSE = "Intel | BSD-3-Clause | GPL-2.0-only"
LIC_FILES_CHKSUM = "file://source/compiler/aslcompile.c;beginline=7;endline=150;md5=41a76b4b1f816240f090cf010fefebf0"

COMPATIBLE_HOST = "(i.86|x86_64|arm|aarch64).*-linux"

DEPENDS = "m4-native flex-native bison-native"

SRC_URI = "https://downloadmirror.intel.com/774879/acpica-unix-${PV}.tar.gz"
SRC_URI[sha256sum] = "acaff68b14f1e0804ebbfc4b97268a4ccbefcfa053b02ed9924f2b14d8a98e21"

UPSTREAM_CHECK_URI = "https://acpica.org/downloads"

S = "${WORKDIR}/acpica-unix-${PV}"

inherit update-alternatives

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE:${PN} = "acpixtract acpidump"

EXTRA_OEMAKE = "CC='${CC}' \
                OPT_CFLAGS=-Wall \
                DESTDIR=${D} \
                PREFIX=${prefix} \
                INSTALLDIR=${bindir} \
                INSTALLFLAGS= \
                YACC=bison \
                YFLAGS='-y --file-prefix-map=${WORKDIR}=/usr/src/debug/${PN}/${EXTENDPE}${PV}-${PR}' \
                "

do_install() {
    oe_runmake install
}

# iasl*.bb is a subset of this recipe, so RREPLACE it
PROVIDES = "iasl"
RPROVIDES:${PN} += "iasl"
RREPLACES:${PN} += "iasl"
RCONFLICTS:${PN} += "iasl"

BBCLASSEXTEND = "native"
