package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class SaleReportDetailModel(
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
        @SerializedName("billAmount")
        val billAmount: Int,
        @SerializedName("billCode")
        val billCode: Int,
        @SerializedName("billDate")
        val billDate: String,
        @SerializedName("billTime")
        val billTime: String,
        @SerializedName("details")
        val details: List<Detail>,
        @SerializedName("docNo")
        val docNo: Int,
        @SerializedName("locationCode")
        val locationCode: Int,
        @SerializedName("s_BillDate")
        val sBillDate: String
    ) {
        data class Detail(
            @SerializedName("billCode")
            val billCode: Int,
            @SerializedName("discAmount")
            val discAmount: Int,
            @SerializedName("itemAmount")
            val itemAmount: Double,
            @SerializedName("quantity")
            val quantity: Int,
            @SerializedName("rate")
            val rate: Double,
            @SerializedName("rawCode")
            val rawCode: String,
            @SerializedName("rawDesc")
            val rawDesc: String,
            @SerializedName("vatAmount")
            val vatAmount: Int
        )
    }
}