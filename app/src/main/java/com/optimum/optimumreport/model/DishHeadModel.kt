package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class DishHeadModel(
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
        @SerializedName("askQty")
        val askQty: Int,
        @SerializedName("dishHeadCode")
        val dishHeadCode: Int,
        @SerializedName("dishHeadName")
        val dishHeadName: String,
        @SerializedName("dishTypeCode")
        val dishTypeCode: Int,
        @SerializedName("dishTypeName")
        val dishTypeName: String,
        @SerializedName("isSync")
        val isSync: Int,
        @SerializedName("locationCode")
        val locationCode: Int,
        @SerializedName("seriesNo")
        val seriesNo: Int
    )
}