package com.example.getcontents.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.getcontents.R
import com.example.getcontents.extensions.Extensions.startActivityWithFinish
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivityWithFinish(LoginActivity::class.java)
    }

    override fun onBackPressed() {}
}