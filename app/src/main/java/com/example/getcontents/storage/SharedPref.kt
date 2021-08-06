package com.example.getcontents.storage

import android.content.Context
import android.util.Log

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

    fun getString(key: String) = sharedPreferences!!.getString(key, null)

    fun getToken(key: String): String = sharedPreferences!!.getString(key, "empty")!!


}