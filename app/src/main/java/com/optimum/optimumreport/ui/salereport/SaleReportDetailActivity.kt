package com.optimum.optimumreport.ui.salereport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.optimum.optimumreport.adapter.SaleReportDetailAdapter
import com.optimum.optimumreport.databinding.ActivitySaleReportBinding
import com.optimum.optimumreport.interfaces.OnInternetCheckListener
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel

class SaleReportDetailActivity : AppCompatActivity() {

    lateinit var viewModel: CafePosViewModel
    lateinit var binding: ActivitySaleReportBinding
    var locationCode = ""
    var billCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaleReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include.tvTitle.text = "Sale Report Detail"

        locationCode = intent.getStringExtra("LOCATION_CODE").toString()
        billCode = intent.getStringExtra("BILL_CODE").toString()

        viewModel = ViewModelProvider(this@SaleReportDetailActivity)[CafePosViewModel::class.java]


        if (Utility.isAppOnLine(this@SaleReportDetailActivity, object : OnInternetCheckListener {
                override fun onInternetAvailable() {
                    getSaleReportDetail()
                }
            })) {
            getSaleReportDetail()
        }
    }

    private fun getSaleReportDetail() {
        Log.e("TAG", "getSaleReportDetail: "+locationCode +" "+billCode )
        viewModel.getSaleReportDetail(locationCode, billCode)
            .observe(this@SaleReportDetailActivity) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        binding.rvSaleListDetail.layoutManager =
                            LinearLayoutManager(this@SaleReportDetailActivity)
                        binding.rvSaleListDetail.adapter =
                            SaleReportDetailAdapter(response.data!!.data.details)
                    }
                    is NetworkResult.Error -> {
                        Utility.showToast(
                            this@SaleReportDetailActivity,
                            response.message.toString()
                        )
                    }
                }
            }
    }
}