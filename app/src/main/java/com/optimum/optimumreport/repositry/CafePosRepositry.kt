package com.optimum.optimumreport.repositry

import com.optimum.optimumreport.retrofit.ApiInterface
import com.google.gson.JsonObject

class CafePosRepositry(val apiInterface: ApiInterface) {

  suspend  fun getLoginResponse(jsonObject: JsonObject)=apiInterface.getLoginResponse(jsonObject)
  suspend  fun getOutletLocationList(userCode:Int)=apiInterface.getOutletLocationList(userCode)
  suspend  fun getSaleData(jsonObject: JsonObject)=apiInterface.getSaleData(jsonObject)
  suspend  fun getSaleReportDetail(locCode:String,billcode:String)=apiInterface.getSaleReportDetail(locCode,billcode)

  suspend  fun getPurchaseData(jsonObject: JsonObject)=apiInterface.getPurchaseData(jsonObject)
  suspend  fun getPurchseReportDetail(locCode:String,billcode:String)=apiInterface.getPurchseReportDetail(locCode,billcode)
  suspend  fun getReport(userCode:String)=apiInterface.getReport(userCode)
  suspend  fun getDishHead()=apiInterface.getDishHead()
  suspend  fun stockItemsList(jsonObject: JsonObject)=apiInterface.stockItemsList(jsonObject)





}