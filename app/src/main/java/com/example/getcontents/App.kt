package com.example.getcontents

import android.app.Application
import com.example.getcontents.storage.SharedPref

class App: Application() {
    companion object {
        lateinit var sharedPref : SharedPref
    }

    override fun onCreate() {
        super.onCreate()
        sharedPref = SharedPref(applicationContext)
    }
}