package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class PurchaseReportDetailModel(
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
        @SerializedName("billamt")
        val billamt: Int,
        @SerializedName("bldate")
        val bldate: String,
        @SerializedName("blno")
        val blno: Int,
        @SerializedName("details")
        val details: List<Detail>,
        @SerializedName("locationCode")
        val locationCode: Int,
        @SerializedName("purcdate")
        val purcdate: String,
        @SerializedName("purcno")
        val purcno: String,
        @SerializedName("s_BlDate")
        val sBlDate: String,
        @SerializedName("s_Purcdate")
        val sPurcdate: String
    ) {
        data class Detail(
            @SerializedName("amount")
            val amount: Int,
            @SerializedName("blno")
            val blno: Int,
            @SerializedName("purcrate")
            val purcrate: Int,
            @SerializedName("qty")
            val qty: Int,
            @SerializedName("rawCode")
            val rawCode: String,
            @SerializedName("rawDesc")
            val rawDesc: String,
            @SerializedName("srNo")
            val srNo: Int
        )
    }
}