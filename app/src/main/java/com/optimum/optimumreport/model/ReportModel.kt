package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class ReportModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("success")
    val success: String
) {
    data class Data(
        @SerializedName("reportCode")
        val reportCode: Int,
        @SerializedName("reportName")
        val reportName: String,
        @SerializedName("userCode")
        val userCode: Int
    )
}