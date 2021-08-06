package com.example.getcontents.assignment

import com.example.getcontents.network.dto.UnitsDto
import com.example.getcontents.network.dto.UserDto

data class AssignmentResult(
    var result: ArrayList<PostResult>
)


data class PostResult(
    var name:String = "",
    var start:String = "",
    var end : String = "",
    val units: ArrayList<UnitsDto>? = null

)