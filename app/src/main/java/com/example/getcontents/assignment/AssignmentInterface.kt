package com.example.getcontents.assignment

import com.example.getcontents.login.LoginDto
import com.example.getcontents.login.LoginInterface
import com.example.getcontents.login.login_Result
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AssignmentInterface {
    @Headers(HEADERS)//@Header로 json임을 명시
    @POST(CALL_ASSIGNMENT)
    fun requestAssignment(
        @Header(AUTHORIZATION) token: String
    ) : Call<AssignmentResult>
    companion object {
        private const val BASE_URL = "https://api.super-brain.co.kr/"
        private const val HEADERS = "Content-Type:application/json"
        private const val AUTHORIZATION = "Authorization"
        private const val CALL_ASSIGNMENT = "assignment/get/list"
    }
}