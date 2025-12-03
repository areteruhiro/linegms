package app.revanced.patches.line.misc.appname

import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.BytecodePatchContext
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patcher.patch.stringOption
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction21c
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction21c
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.android.tools.smali.dexlib2.immutable.reference.ImmutableStringReference
import com.android.tools.smali.dexlib2.util.MethodUtil

@Suppress("unused")
val lineGlobalPackageStringPatch = bytecodePatch(
    name = "LINE package string rewrite",
    description = "Replace all \"jp.naver.line.android\" string literals with the specified package name.",
) {
    compatibleWith(
        "jp.naver.line.android",
        "jp.naver.line.androidd",
    )

    val customPackageOption = stringOption(
        key = "lineCustomPackageName",
        default = "jp.naver.line.androidd",
        title = "Target LINE package name",
        description = "All string literals exactly equal to \"jp.naver.line.android\" will be rewritten to this value.",
        required = true,
    )

    val customPackageName by customPackageOption

    execute {
        val original = "jp.naver.line.android"

        val target = customPackageName
            ?.trim()
            ?.ifEmpty { "jp.naver.line.androidd" }

        transformStringReferences { str ->
            if (str == original) {
                target
            } else {
                null
            }
        }
    }
}

private fun BytecodePatchContext.transformStringReferences(
    transform: (String) -> String?,
) = classes.forEach { classDef ->

    val mutableClass by lazy { proxy(classDef).mutableClass }

    classDef.methods.forEach methodLoop@ { method ->
        val impl = method.implementation ?: return@methodLoop

        val mutableMethod by lazy {
            mutableClass.methods.first {
                MethodUtil.methodSignaturesMatch(it, method)
            }
        }

        impl.instructions.forEachIndexed insnLoop@ { index, insn ->
            val i21c = insn as? Instruction21c ?: return@insnLoop
            val stringRef = i21c.reference as? StringReference ?: return@insnLoop

            val original = stringRef.string
            val replaced = transform(original) ?: return@insnLoop

            mutableMethod.replaceInstruction(
                index,
                BuilderInstruction21c(
                    Opcode.CONST_STRING,
                    i21c.registerA,
                    ImmutableStringReference(replaced),
                ),
            )
        }
    }
}
