package app.revanced.patches.line.misc.gms

import app.revanced.patcher.patch.Option
import app.revanced.patches.line.misc.extension.sharedExtensionPatch
import app.revanced.patches.line.misc.gms.fingerprints.MainActivityFingerprint
import app.revanced.patches.shared.castContextFetchFingerprint
import app.revanced.patches.shared.misc.gms.gmsCoreSupportPatch
import app.revanced.patches.shared.primeMethodFingerprint

internal object LineConstants {
    const val MICROG_SIGNATURE = "1be388ce61a43b6a0b60b42928b6cbe0d83b46a8e873aa193e25f0a589d230c8"
}

@Suppress("unused")
val gmsCoreSupportPatch = gmsCoreSupportPatch(
    fromPackageName = Constants.LINE_PACKAGE_NAME,
    toPackageName = Constants.REVANCED_LINE_PACKAGE_NAME,
    primeMethodFingerprint = primeMethodFingerprint,
    earlyReturnFingerprints = setOf(
        castContextFetchFingerprint
    ),
    mainActivityOnCreateFingerprint = MainActivityFingerprint,
    extensionPatch = sharedExtensionPatch,
    gmsCoreSupportResourcePatchFactory = ::lineGmsCoreSupportResourcePatch,
) {
    compatibleWith(Constants.LINE_PACKAGE_NAME)
}

private fun lineGmsCoreSupportResourcePatch(
    gmsCoreVendorGroupIdOption: Option<String>,
) = app.revanced.patches.shared.misc.gms.gmsCoreSupportResourcePatch(
    fromPackageName = Constants.LINE_PACKAGE_NAME,
    toPackageName = Constants.REVANCED_LINE_PACKAGE_NAME,
    spoofedPackageSignature = LineConstants.MICROG_SIGNATURE,
    gmsCoreVendorGroupIdOption = gmsCoreVendorGroupIdOption,
)
