package app.revanced.patches.line.misc.fix

import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.indexOfFirstInstructionReversedOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

@Suppress("unused")
val spoofSignaturePatch = bytecodePatch(
    name = "LINE signature",
    description = "LINE the signature of the app to fix various functions of the app.",
) {
    compatibleWith("jp.naver.line.android")

    execute {
        getAppSignatureFingerprint.method.apply {
            val failedToGetSignaturesStringMatch = getAppSignatureFingerprint.stringMatches!!.first()

            val concatSignaturesIndex = indexOfFirstInstructionReversedOrThrow(
                failedToGetSignaturesStringMatch.index,
                Opcode.MOVE_RESULT_OBJECT,
            )

            val register = getInstruction<OneRegisterInstruction>(concatSignaturesIndex).registerA

            val expectedSignature = "89396dc419292473972813922867e6973d6f5c50"

            replaceInstruction(concatSignaturesIndex, "const-string v$register, \"$expectedSignature\"")
        }
    }
}

