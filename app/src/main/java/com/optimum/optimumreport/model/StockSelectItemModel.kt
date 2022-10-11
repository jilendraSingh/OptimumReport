package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class StockSelectItemModel(
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
        @SerializedName("activeItem")
        val activeItem: Int,
        @SerializedName("deptCode")
        val deptCode: Int,
        @SerializedName("disc")
        val disc: Int,
        @SerializedName("discountable")
        val discountable: Int,
        @SerializedName("dishHeadCode")
        val dishHeadCode: Int,
        @SerializedName("hd")
        val hd: Double,
        @SerializedName("hsnCode")
        val hsnCode: Int,
        @SerializedName("itemGroupCode")
        val itemGroupCode: Int,
        @SerializedName("kitchenCode")
        val kitchenCode: Int,
        @SerializedName("locationCode")
        val locationCode: Int,
        @SerializedName("rate")
        val rate: Double,
        @SerializedName("rateTakeaway")
        val rateTakeaway: Double,
        @SerializedName("rawCode")
        val rawCode: String,
        @SerializedName("rawCodeNew")
        val rawCodeNew: String,
        @SerializedName("rawDesc")
        val rawDesc: String,
        @SerializedName("rawName")
        val rawName: String,
        @SerializedName("taxIncludeRate")
        val taxIncludeRate: Int,
        @SerializedName("taxable")
        val taxable: Int,
        @SerializedName("unitCode")
        val unitCode: Int,
        @SerializedName("userCode")
        val userCode: Int
    )
}