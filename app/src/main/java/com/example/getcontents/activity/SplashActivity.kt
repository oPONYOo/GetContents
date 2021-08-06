package com.example.getcontents.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.getcontents.R

private const val SPLASH_VIEW_TIME: Long = 2000 //2초간 스플래시 화면을 보여줌 (ms)
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({ //delay를 위한 handler
            val nextIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextIntent)
            this.finish()
        }, SPLASH_VIEW_TIME)
    }

    override fun onBackPressed() {}
}