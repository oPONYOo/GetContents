package com.example.getcontents.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UnitsDto(
    @SerializedName("unit") val unit: String,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("type") val type: String,
    @SerializedName("section") val section: String,
    @SerializedName("section_detail") val sectionDetail: String? = null,
    @SerializedName("progress") var progress: Int
) : Serializable