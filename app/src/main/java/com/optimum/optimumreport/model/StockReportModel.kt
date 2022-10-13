package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class StockReportModel(
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
        @SerializedName("closing")
        val closing: Int,
        @SerializedName("inward")
        val inward: Int,
        @SerializedName("itemRate")
        val itemRate: Double,
        @SerializedName("opening")
        val opening: Int,
        @SerializedName("outward")
        val outward: Int,
        @SerializedName("qty")
        val qty: Int,
        @SerializedName("rawCode")
        val rawCode: String,
        @SerializedName("rawDesc")
        val rawDesc: String
    )
}