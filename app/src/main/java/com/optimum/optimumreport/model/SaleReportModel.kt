package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class SaleReportModel(
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
        @SerializedName("billAmount")
        val billAmount: Double,
        @SerializedName("billCode")
        val billCode: Int,
        @SerializedName("billDate")
        val billDate: String,
        @SerializedName("billDocNo")
        val billDocNo: String,
        @SerializedName("billTime")
        val billTime: String,
        @SerializedName("docNo")
        val docNo: Int,
        @SerializedName("locationCode")
        val locationCode: Int,
        @SerializedName("s_BillDate")
        val sBillDate: String,
        @SerializedName("vCode")
        val vCode: String
    )
}