package app.revanced.patches.line.misc.extension.hooks

import app.revanced.patches.shared.misc.extension.extensionHook


// Extension context is the Activity itself.
internal val applicationInitHook = extensionHook {
    custom { methodDef, classDef ->
        methodDef.name == "onCreate" && classDef.type == "Ljp/naver/line/android/activity/SplashActivity;"
    }
}
