package app.revanced.patches.line.misc.gms

import app.revanced.patcher.patch.Option
import app.revanced.patches.line.misc.extension.sharedExtensionPatch
import app.revanced.patches.line.misc.gms.fingerprints.MainActivityFingerprint
import app.revanced.patches.shared.misc.gms.gmsCoreSupportPatch
import app.revanced.patches.shared.primeMethodFingerprint


@Suppress("unused")
val gmsCoreSupportPatch = gmsCoreSupportPatch(
    fromPackageName = Constants.LINE_PACKAGE_NAME,
    toPackageName = Constants.REVANCED_LINE_PACKAGE_NAME,
    extensionPatch = sharedExtensionPatch,
    mainActivityOnCreateFingerprint = MainActivityFingerprint,
    gmsCoreSupportResourcePatchFactory = ::lineGmsCoreSupportResourcePatch,
) {
    compatibleWith(Constants.LINE_PACKAGE_NAME)
}

private fun lineGmsCoreSupportResourcePatch(
    gmsCoreVendorGroupIdOption: Option<String>,
) = app.revanced.patches.shared.misc.gms.gmsCoreSupportResourcePatch(
    fromPackageName = Constants.LINE_PACKAGE_NAME,
    toPackageName = Constants.REVANCED_LINE_PACKAGE_NAME,
    spoofedPackageSignature = "89396dc419292473972813922867e6973d6f5c50",
    gmsCoreVendorGroupIdOption = gmsCoreVendorGroupIdOption,
)
