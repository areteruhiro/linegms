package app.revanced.patches.line.misc.appname

import app.revanced.patcher.patch.resourcePatch

@Suppress("unused")
val lineRemoveUnsupportedAttrsPatch = resourcePatch(
    name = "LINE remove unsupported attrs",
    description = "Remove unsupported attributes like android:useLocalePreferredLineHeightForMinimum so that aapt2 can compile resources.",
) {
    compatibleWith(
        "jp.naver.line.android",
        "jp.naver.line.androidd",
    )

    execute {
        val targets = listOf(
            "res/layout/fragment_camera_image_text_dialog.xml",
            "res/layout/voom_camera_editor_impl_fragment_text_compose_dialog.xml",
            "res/layout/voom_camera_editor_impl_fragment_text_dialog.xml",
        )

        targets.forEach { path ->
            runCatching {
                val file = get(path)
                val original = file.readText()
                
                val replaced = original
                    .replace("""android:useLocalePreferredLineHeightForMinimum="true"""", "")
                    .replace("""android:useLocalePreferredLineHeightForMinimum="false"""", "")

                if (replaced != original) {
                    file.writeText(replaced)
                }
            }
        }
    }
}
