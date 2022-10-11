package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class PurchaseReportModel(
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
        @SerializedName("billamt")
        val billamt: Double,
        @SerializedName("bldate")
        val bldate: String,
        @SerializedName("blno")
        val blno: Int,
        @SerializedName("locationCode")
        val locationCode: Int,
        @SerializedName("purcno")
        val purcno: String,
        @SerializedName("s_BlDate")
        val sBlDate: String,
        @SerializedName("vcode")
        val vcode: String,
        @SerializedName("vseries")
        val vseries: String,
        @SerializedName("yearcode")
        val yearcode: Int
    )
}