package com.example.getcontents.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.getcontents.App

import com.example.getcontents.R
import com.example.getcontents.databinding.ActivityLoginBinding
import com.example.getcontents.extensions.Extensions.startActivityWithFinish
import com.example.getcontents.login.*
import com.example.getcontents.network.*
import com.example.getcontents.storage.SharedPref
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var newToken: String? = null
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            lifecycleOwner = this@LoginActivity
        }

        binding.loginBtn.setOnClickListener(this::onClick)

    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.loginBtn -> {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .build()
                val loginService: LoginInterface = retrofit.create(LoginInterface::class.java)

                loginService.requestLogin(
                    LoginDto(
                        binding.textInputTxtView1.text.toString(),
                        binding.textInputTxtView2.text.toString()
                    )
                ).enqueue(object : Callback<login_Result> {
                    override fun onResponse(
                        call: Call<login_Result>,
                        response: Response<login_Result>
                    ) {
                        if (response.isSuccessful) {
                            val result = response.body()!!.result
                            Log.e("access", result.access)
                            Log.e("refresh", result.refresh)
                            SharedPref.apply {
                                App.sharedPref.storedToken(
                                    PREF_KEEP_LOGIN_TOKEN,
                                    result.access
                                )

                                App.sharedPref.storedToken(
                                    PREF_REFRESH_LOGIN_TOKEN,
                                    result.refresh
                                )

                                App.sharedPref.putString(
                                    PREF_USER_INFO,
                                    gson.toJson(result.user)
                                )


                                startActivityWithFinish(MainActivity::class.java)
                            }
                        }else if (response.toString().contains("401")){
                            val dialog = AlertDialog.Builder(this@LoginActivity)
                            dialog.setMessage("아이디 또는 비밀번호를 확인해주세요.")
                            dialog.setPositiveButton("확인",null)
                            dialog.show()
                        }

                    }

                    override fun onFailure(call: Call<login_Result>, t: Throwable) {
                        Log.e("LOGIN", "$t")
                        val dialog = AlertDialog.Builder(this@LoginActivity)
                        dialog.setTitle("에러")
                        dialog.setMessage("호출실패했습니다.")
                        dialog.show()
                    }

                })

            }

        }
    }

    companion object {
        private const val BASE_URL = "https://api.super-brain.co.kr/"
    }

}