package com.example.getcontents.login

import com.example.getcontents.network.dto.UserDto
import com.google.gson.annotations.SerializedName

data class login_Result(
    var result: PostResult
)

data class PostResult(
    var access:String = "",
    var refresh:String = "",
    var user : UserDto
)