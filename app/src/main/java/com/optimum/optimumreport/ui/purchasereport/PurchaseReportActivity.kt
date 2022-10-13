package com.optimum.optimumreport.ui.purchasereport

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.optimum.optimumreport.adapter.PurchaseReportAdapter
import com.optimum.optimumreport.databinding.ActivityPurchaseReportBinding
import com.optimum.optimumreport.interfaces.OnInternetCheckListener
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*

class PurchaseReportActivity : AppCompatActivity(), OnItemClickListener {

    lateinit var binding: ActivityPurchaseReportBinding
    lateinit var viewModel: CafePosViewModel
    var locationCode = ""
    var cal = Calendar.getInstance()
    var isFromDate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include.tvTitle.text = "Purchase Report"

        locationCode = intent.getStringExtra("LOCATION_CODE").toString()
        viewModel = ViewModelProvider(this@PurchaseReportActivity)[CafePosViewModel::class.java]

        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvFromDate.text = sdf.format(cal.getTime())
        binding.tvTodate.text = sdf.format(cal.getTime())

        binding.tvFromDate.setOnClickListener {
            isFromDate = false
            showDatePicker()
        }
        binding.tvTodate.setOnClickListener {
            isFromDate = true
            showDatePicker()
        }

        binding.btnSubmit.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            binding.rvSaleReportList.visibility = View.GONE

            Handler(Looper.getMainLooper()).postDelayed(Runnable {

                if (Utility.isAppOnLine(
                        this@PurchaseReportActivity,
                        object : OnInternetCheckListener {
                            override fun onInternetAvailable() {
                                purchaseReportData()
                            }
                        })
                ) {
                    purchaseReportData()
                }

            }, 500)


        }
    }

    fun showDatePicker() {

        DatePickerDialog(
            this@PurchaseReportActivity,
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()


    }

    private fun purchaseReportData() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("locationCode", locationCode)
        jsonObject.addProperty("startDate", binding.tvFromDate.text.toString())
        jsonObject.addProperty("endDate", binding.tvTodate.text.toString())
        viewModel.getPurchaseData(jsonObject).observe(this@PurchaseReportActivity) { response ->
            when (response) {

                is NetworkResult.Success -> {
                    if (response.data!!.data.isNotEmpty()) {
                        binding.rvSaleReportList.layoutManager =
                            LinearLayoutManager(this@PurchaseReportActivity)
                        binding.rvSaleReportList.isNestedScrollingEnabled = false
                        binding.rvSaleReportList.adapter =
                            PurchaseReportAdapter(response.data.data, this)
                        binding.progressCircular.visibility = View.GONE
                        binding.rvSaleReportList.visibility = View.VISIBLE

                        var billAmount = 0.0
                        for (item in response.data.data) {
                            billAmount += item.billamt
                        }
                        binding.tvTotalAmount.text = billAmount.toString()
                        binding.llLinearAmountLayout.visibility=View.VISIBLE

                    } else {
                        Utility.showToast(this@PurchaseReportActivity, "Data Not Found!")
                        binding.progressCircular.visibility = View.GONE
                        binding.llLinearAmountLayout.visibility=View.GONE
                    }

                }

                is NetworkResult.Error -> {
                    Utility.showToast(this@PurchaseReportActivity, response.message.toString())
                }
            }
        }
    }

    override fun onItemClick(position: Int, data: String) {
        val intent = Intent(this@PurchaseReportActivity, PurchaseReportDetailActivity::class.java)
        intent.putExtra("LOCATION_CODE", "8")
        intent.putExtra("BILL_CODE", data)
        startActivity(intent)

    }

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        if (isFromDate) {
            binding.tvTodate.text = sdf.format(cal.getTime())
        } else {
            binding.tvFromDate.text = sdf.format(cal.getTime())
        }

    }
}