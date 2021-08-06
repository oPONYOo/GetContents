package com.example.getcontents.login

import retrofit2.Call
import retrofit2.http.*

interface LoginInterface {
    @Headers(HEADERS)//@Header로 json임을 명시
    @POST(USER_LOGIN)
    fun requestLogin(
        @Body body: LoginDto
    ) : Call<login_Result>

    companion object {
        private const val BASE_URL = "https://api.super-brain.co.kr/"
        private const val HEADERS = "Content-Type:application/json"
        private const val AUTHORIZATION = "Authorization"
        private const val USER_LOGIN = "user/check/login"
    }

}