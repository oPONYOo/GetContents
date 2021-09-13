package com.example.getcontents.storage

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.getcontents.activity.BaseActivity
import com.example.getcontents.activity.LoginActivity
import com.example.getcontents.activity.MainActivity
import com.example.getcontents.activity.SplashActivity
import com.example.getcontents.extensions.Extensions.startActivity
import com.example.getcontents.extensions.Extensions.startActivityWithFinish

class SharedPref(context: Context) {
    companion object {

        /** SharedPreference NAME */
        private const val PREF_NAME = "SharedPreferenceStore"

        /** Token info key */
        const val PREF_KEEP_LOGIN_TOKEN = "PREF_KEEP_LOGIN_TOKEN"
        const val PREF_REFRESH_LOGIN_TOKEN = "PREF_REFRESH_LOGIN_TOKEN"
        const val PREF_USER_ID = "PREF_USER_ID"
        const val PREF_USER_INFO = "PREF_USER_INFO"
        const val PREF_MSG_TOKEN = "PREF_MSG_TOKEN"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun storedToken(key: String, set: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, set)
        editor.apply()
    }

    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }


    fun getString(key: String) = sharedPreferences!!.getString(key, null)

    fun getInt(key: String) = sharedPreferences!!.getInt(key, 0)
    fun getToken(key: String): String = sharedPreferences!!.getString(key, "empty")!!


    fun checkToken(key: String, activity: BaseActivity) {
        val reload = getToken(key)
        if (reload != "empty") {
            Log.i("reload", reload)
            activity.startActivityWithFinish(MainActivity::class.java)
        }
    }

    fun forcedLogout(activity: BaseActivity) {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        activity.startActivity(LoginActivity::class.java)
        activity.finishAffinity()
    }
}