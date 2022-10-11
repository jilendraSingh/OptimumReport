package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("success")
    val success: String
) {
    data class Data(
        @SerializedName("category")
        val category: Int,
        @SerializedName("userCode")
        val userCode: Int,
        @SerializedName("userName")
        val userName: String
    )
}