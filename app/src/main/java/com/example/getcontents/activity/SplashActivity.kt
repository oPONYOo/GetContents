package com.example.getcontents.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.getcontents.R
import com.example.getcontents.extensions.Extensions.startActivityWithFinish
import kotlinx.coroutines.*

private const val SPLASH_VIEW_TIME: Long = 2000 //2초간 스플래시 화면을 보여줌 (ms)
val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(mainDispatcher).launch {
            delay(SPLASH_VIEW_TIME)
            startActivityWithFinish(LoginActivity::class.java)
        }
    }

    override fun onBackPressed() {}
}