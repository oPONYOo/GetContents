package com.example.getcontents.token

import com.example.getcontents.login.PostResult
import com.example.getcontents.network.dto.UserDto
import com.google.gson.annotations.SerializedName

data class ReissueTokenResult(@SerializedName("result") val result: String? = null): Result()

open class Result(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("message") val message: String? = null
)