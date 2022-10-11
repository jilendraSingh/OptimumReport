package com.optimum.optimumreport.model


import com.google.gson.annotations.SerializedName

data class OutletModel(
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
        @SerializedName("address")
        val address: String,
        @SerializedName("companyCode")
        val companyCode: Int,
        @SerializedName("companyName")
        val companyName: String,
        @SerializedName("gstno")
        val gstno: String,
        @SerializedName("locations")
        val locations: List<Location>
    ) {
        data class Location(
            @SerializedName("address")
            val address: String,
            @SerializedName("companyCode")
            val companyCode: Int,
            @SerializedName("locationCode")
            val locationCode: Int,
            @SerializedName("locationName")
            val locationName: String
        )
    }
}