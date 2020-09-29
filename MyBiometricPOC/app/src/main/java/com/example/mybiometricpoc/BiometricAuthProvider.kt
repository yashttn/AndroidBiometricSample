package com.example.mybiometricpoc

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat


class BiometricAuthProvider(val context: Context?) {

    private val TAG = this::class.java.simpleName

    val biometricManager = context?.let { BiometricManager.from(it) }

    fun showFingerprintScreen() {
        if (verifyBiometricExistence()) {
            val executor = ContextCompat.getMainExecutor(context)
            val biometricPrompt = BiometricPrompt(context as AppCompatActivity,
                executor, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(
                            context, "Fingerprint Auth Success!", Toast.LENGTH_SHORT
                        ).show()
                        (context as? MainActivity)?.changeImage()
                    }

                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(
                            context, "Fingerprint Auth Failed! Try Again!", Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(
                            context, "Fingerprint Auth Failed! Try Again!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Fingerprint Biometric Login")
                .setSubtitle("Log in using your fingerprint")
                .setDeviceCredentialAllowed(true)
                .build()
            biometricPrompt.authenticate(promptInfo)
        } else {
            Log.e(TAG, "Cannot use Fingerprint Scanner!")
            Toast.makeText(context, "Cannot use Fingerprint Scanner!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun verifyBiometricExistence(): Boolean {
        when (biometricManager?.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d(TAG, "App can authenticate using biometrics.")
                return true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.d(TAG, "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.d(TAG, "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Log.d(
                    TAG,
                    "The user hasn't associated any biometric credentials with their account."
                )
        }
        return false
    }

}