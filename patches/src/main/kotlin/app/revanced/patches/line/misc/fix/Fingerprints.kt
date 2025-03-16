package app.revanced.patches.line.misc.fix


import app.revanced.patcher.fingerprint

internal val getAppSignatureFingerprint = fingerprint { strings("Signature check failed for ") }
