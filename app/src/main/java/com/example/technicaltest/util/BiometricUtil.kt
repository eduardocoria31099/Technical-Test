package com.example.technicaltest.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat


fun AppCompatActivity.useBiometric(
    biometricModel: BiometricModel
): BiometricPrompt {
    biometricModel.apply {
        return BiometricPrompt(
            this@useBiometric, ContextCompat.getMainExecutor(this@useBiometric),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    authenticationError.invoke()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    authenticationSucceeded.invoke()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    authenticationFailed.invoke()
                }
            }).apply {
            authenticate(
                BiometricPrompt.PromptInfo.Builder()
                    .setTitle(textTitle)
                    .setSubtitle(textSubTitle)
                    .setNegativeButtonText(textNegativeButton)
                    .build()
            )
        }
    }
}

fun Context.isBiometricAvailable(): Boolean = BiometricManager.from(this)
    .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
