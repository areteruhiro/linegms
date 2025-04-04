package app.revanced.patches.shared.misc.gms

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

const val GET_GMS_CORE_VENDOR_GROUP_ID_METHOD_NAME = "getGmsCoreVendorGroupId"


internal val gmsCoreSupportFingerprint = fingerprint {
    custom { _, classDef ->
        classDef.endsWith("GmsCoreSupport;")
    }
}


internal val serviceCheckFingerprint = fingerprint {
    strings("Google Play Services not available")
}

