package com.optimum.optimumreport.ui.purchasereport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.optimum.optimumreport.adapter.PurchaseReportDetailAdapter
import com.optimum.optimumreport.databinding.ActivityPurchaseReportDetailBinding
import com.optimum.optimumreport.interfaces.OnInternetCheckListener
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel

class PurchaseReportDetailActivity : AppCompatActivity() {

    lateinit var viewModel: CafePosViewModel
    lateinit var binding: ActivityPurchaseReportDetailBinding
    var locationCode = ""
    var billCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include.tvTitle.text = "Purchase Report Detail"

        viewModel =
            ViewModelProvider(this@PurchaseReportDetailActivity)[CafePosViewModel::class.java]

        locationCode = intent.getStringExtra("LOCATION_CODE").toString()
        billCode = intent.getStringExtra("BILL_CODE").toString()

        if (Utility.isAppOnLine(
                this@PurchaseReportDetailActivity,
                object : OnInternetCheckListener {
                    override fun onInternetAvailable() {
                        getPurchaseReportDetail()
                    }
                })
        ) {
            getPurchaseReportDetail()
        }
    }

    private fun getPurchaseReportDetail() {
        viewModel.getPurchseReportDetail(locationCode, billCode)
            .observe(this@PurchaseReportDetailActivity) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        binding.rvSaleListDetail.layoutManager =
                            LinearLayoutManager(this@PurchaseReportDetailActivity)
                        binding.rvSaleListDetail.adapter =
                            PurchaseReportDetailAdapter(response.data!!.data.details)
                    }
                    is NetworkResult.Error -> {
                        Utility.showToast(
                            this@PurchaseReportDetailActivity,
                            response.message.toString()
                        )
                    }
                }
            }
    }
}