package com.optimum.optimumreport.retrofit

import com.optimum.optimumreport.model.*
import com.optimum.optimumreport.utility.URLConstant
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST(URLConstant.URL_LOGIN_URL)
    suspend fun getLoginResponse(
        @Body jsonObject: JsonObject
    ): Response<LoginModel>

    @Headers("Content-Type: application/json")
    @GET("Company/{usercode}")
    suspend fun getOutletLocationList(
        @Path("usercode") usercode: Int,
    ): Response<OutletModel>

    @Headers("Content-Type: application/json")
    @POST(URLConstant.URL_SALE_REPORT_URL)
    suspend fun getSaleData(
        @Body jsonObject: JsonObject
    ): Response<SaleReportModel>


    @Headers("Content-Type: application/json")
    @GET("http://optimum.co.in/cafeposapp/Bill/getbill/{locationcode}/{billcode}")
    suspend fun getSaleReportDetail(
        @Path("locationcode") locationcode: String,
        @Path("billcode") billcode: String,
    ): Response<SaleReportDetailModel>


    @Headers("Content-Type: application/json")
    @POST(URLConstant.URL_PURCHASE_REPORT_URL)
    suspend fun getPurchaseData(
        @Body jsonObject: JsonObject
    ): Response<PurchaseReportModel>

    @Headers("Content-Type: application/json")
    @GET("http://optimum.co.in/cafeposapp/Purchase/getpurchase/{locationcode}/{blno}")
    suspend fun getPurchseReportDetail(
        @Path("locationcode") locationcode: String,
        @Path("blno") billcode: String,
    ): Response<PurchaseReportDetailModel>


    @Headers("Content-Type: application/json")
    @GET("http://optimum.co.in/cafeposapp/User/getreports/{usercode}")
    suspend fun getReport(
        @Path("usercode") locationcode: String
    ): Response<ReportModel>


    @Headers("Content-Type: application/json")
    @GET("http://optimum.co.in/cafeposapp/Master/getdishheads/{locationcode}")
    suspend fun getDishHead(@Path("locationcode") locationcode: Int): Response<DishHeadModel>



    @Headers("Content-Type: application/json")
    @POST(URLConstant.URL_STOCK_ITEM_URL)
    suspend fun stockItemsList(
        @Body jsonObject: JsonObject
    ): Response<StockSelectItemModel>

    @Headers("Content-Type: application/json")
    @POST(URLConstant.URL_STOCK_ALL_ITEM_URL)
    suspend fun getStockReport(
        @Body jsonObject: JsonObject
    ): Response<StockReportModel>



}