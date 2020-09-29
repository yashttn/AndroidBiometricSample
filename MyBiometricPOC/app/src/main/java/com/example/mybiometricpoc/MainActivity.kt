package com.example.mybiometricpoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentImage = R.drawable.day

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fingerprintAuth()
    }

    fun fingerprintAuth() {
        button?.setOnClickListener {
            val provider = BiometricAuthProvider(this)
            provider.showFingerprintScreen()
        }
    }

    fun faceDetectionAuth(){

    }

    fun changeImage() {
        currentImage = if (currentImage == R.drawable.day) {
            R.drawable.night
        } else {
            R.drawable.day
        }
        imageView.setImageResource(currentImage)
    }

}
