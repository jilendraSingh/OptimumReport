package com.optimum.optimumreport.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optimum.optimumreport.model.*
import com.optimum.optimumreport.repositry.CafePosRepositry
import com.optimum.optimumreport.retrofit.RetrofitClient
import com.optimum.optimumreport.utility.NetworkResult
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class CafePosViewModel : ViewModel() {
    val reportLivatData = MutableLiveData<NetworkResult<ReportModel>>()
    val mutableLiveData = MutableLiveData<NetworkResult<LoginModel>>()
    val outletModel = MutableLiveData<NetworkResult<OutletModel>>()
    val saleReportData = MutableLiveData<NetworkResult<SaleReportModel>>()
    val saleReportDetail = MutableLiveData<NetworkResult<SaleReportDetailModel>>()
    val purchaseReportDetail = MutableLiveData<NetworkResult<PurchaseReportDetailModel>>()
    val purchaseReportData = MutableLiveData<NetworkResult<PurchaseReportModel>>()

    val dishheadLiveData = MutableLiveData<NetworkResult<DishHeadModel>>()
    val stockItemsLiveData = MutableLiveData<NetworkResult<StockSelectItemModel>>()
    val stockReportLiveData = MutableLiveData<NetworkResult<StockReportModel>>()




    fun loginRequest(jsonObject: JsonObject): LiveData<NetworkResult<LoginModel>> {
        viewModelScope.launch {
            val result = CafePosRepositry(RetrofitClient.instance).getLoginResponse(jsonObject)
            if (result.isSuccessful) {
                mutableLiveData.value = NetworkResult.Success(result.body()!!)
            } else {
                mutableLiveData.value = NetworkResult.Error(result.message())
            }
        }
        return mutableLiveData
    }

    fun getOutletLocationList(usercode: Int): LiveData<NetworkResult<OutletModel>> {
        viewModelScope.launch {
            val result = CafePosRepositry(RetrofitClient.instance).getOutletLocationList(usercode)
            if (result.isSuccessful) {
                outletModel.value = NetworkResult.Success(result.body()!!)
            } else {
                outletModel.value = NetworkResult.Error(result.message())
            }
        }
        return outletModel
    }

    fun getSaleData(jsonObject: JsonObject): LiveData<NetworkResult<SaleReportModel>> {
        viewModelScope.launch {
            val result = CafePosRepositry(RetrofitClient.instance).getSaleData(jsonObject)
            if (result.isSuccessful) {
                saleReportData.value = NetworkResult.Success(result.body()!!)
            } else {
                saleReportData.value = NetworkResult.Error(result.message())
            }
        }
        return saleReportData
    }


    fun getSaleReportDetail(
        locCode: String,
        billcode: String
    ): LiveData<NetworkResult<SaleReportDetailModel>> {
        viewModelScope.launch {
            val result =
                CafePosRepositry(RetrofitClient.instance).getSaleReportDetail(locCode, billcode)
            if (result.isSuccessful) {
                saleReportDetail.value = NetworkResult.Success(result.body()!!)
            } else {
                saleReportDetail.value = NetworkResult.Error(result.message())
            }
        }
        return saleReportDetail
    }


    fun getPurchaseData(jsonObject: JsonObject): LiveData<NetworkResult<PurchaseReportModel>> {
        viewModelScope.launch {
            val result = CafePosRepositry(RetrofitClient.instance).getPurchaseData(jsonObject)
            if (result.isSuccessful) {
                purchaseReportData.value = NetworkResult.Success(result.body()!!)
            } else {
                purchaseReportData.value = NetworkResult.Error(result.message())
            }
        }
        return purchaseReportData
    }


    fun getPurchseReportDetail(
        locCode: String,
        billcode: String
    ): LiveData<NetworkResult<PurchaseReportDetailModel>> {
        viewModelScope.launch {
            val result =
                CafePosRepositry(RetrofitClient.instance).getPurchseReportDetail(locCode, billcode)
            if (result.isSuccessful) {
                purchaseReportDetail.value = NetworkResult.Success(result.body()!!)
            } else {
                purchaseReportDetail.value = NetworkResult.Error(result.message())
            }
        }
        return purchaseReportDetail
    }


    fun getReport(
        locCode: String
    ): LiveData<NetworkResult<ReportModel>> {
        viewModelScope.launch {
            val result =
                CafePosRepositry(RetrofitClient.instance).getReport(locCode)
            if (result.isSuccessful) {
                reportLivatData.value = NetworkResult.Success(result.body()!!)
            } else {
                reportLivatData.value = NetworkResult.Error(result.message())
            }
        }
        return reportLivatData
    }

    fun getDishHead(
    ): LiveData<NetworkResult<DishHeadModel>> {
        viewModelScope.launch {
            val result =
                CafePosRepositry(RetrofitClient.instance).getDishHead()
            if (result.isSuccessful) {
                dishheadLiveData.value = NetworkResult.Success(result.body()!!)
            } else {
                dishheadLiveData.value = NetworkResult.Error(result.message())
            }
        }
        return dishheadLiveData
    }

    fun stockItemsList(jsonObject: JsonObject): LiveData<NetworkResult<StockSelectItemModel>> {
        viewModelScope.launch {
            val result = CafePosRepositry(RetrofitClient.instance).stockItemsList(jsonObject)
            if (result.isSuccessful) {
                stockItemsLiveData.value = NetworkResult.Success(result.body()!!)
            } else {
                stockItemsLiveData.value = NetworkResult.Error(result.message())
            }
        }
        return stockItemsLiveData
    }

    fun getStockReport(jsonObject: JsonObject): LiveData<NetworkResult<StockReportModel>> {
        viewModelScope.launch {
            val result = CafePosRepositry(RetrofitClient.instance).getStockReport(jsonObject)
            if (result.isSuccessful) {
                stockReportLiveData.value = NetworkResult.Success(result.body()!!)
            } else {
                stockReportLiveData.value = NetworkResult.Error(result.message())
            }
        }
        return stockReportLiveData
    }




}