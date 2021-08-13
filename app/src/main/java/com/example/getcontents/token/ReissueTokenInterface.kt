package com.example.getcontents.token

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ReissueTokenInterface {
    @Headers(HEADERS)
    @POST(REISSUE_TOKEN)
    fun reissueToken(@Header(AUTHORIZATION) token: String): Call<ReissueTokenResult>
    companion object {
        private const val BASE_URL = "https://api.super-brain.co.kr/"
        private const val HEADERS = "Content-Type:application/json"
        private const val AUTHORIZATION = "Authorization"
        private const val REISSUE_TOKEN = "user/reissue/token"
    }
}