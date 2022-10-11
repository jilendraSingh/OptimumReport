package com.optimum.optimumreport.ui.salereport

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
import com.google.gson.JsonObject
import com.optimum.optimumreport.adapter.SaleReportAdapter
import com.optimum.optimumreport.databinding.FragmentSaleReportBinding
import com.optimum.optimumreport.interfaces.OnInternetCheckListener
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel
import java.text.SimpleDateFormat
import java.util.*

class SaleReportActivity : AppCompatActivity(), OnItemClickListener {

    lateinit var binding: FragmentSaleReportBinding
    lateinit var viewModel: CafePosViewModel
    var locationCode = ""
    var cal = Calendar.getInstance()
    var isFromDate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSaleReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationCode = intent.getStringExtra("LOCATION_CODE").toString()

        binding.include.tvTitle.text = "Sale Report"
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvFromDate.text = sdf.format(cal.getTime())
        binding.tvTodate.text = sdf.format(cal.getTime())

        viewModel = ViewModelProvider(this@SaleReportActivity)[CafePosViewModel::class.java]


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

                if (Utility.isAppOnLine(this@SaleReportActivity, object : OnInternetCheckListener {
                        override fun onInternetAvailable() {
                            getSaleReportData()
                        }
                    })) {
                    getSaleReportData()
                }

            }, 500)


        }
    }

    fun showDatePicker() {

        DatePickerDialog(
            this@SaleReportActivity,
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()


    }

    private fun getSaleReportData() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("locationCode", locationCode)
        jsonObject.addProperty("startDate", binding.tvFromDate.text.toString())
        jsonObject.addProperty("endDate", binding.tvTodate.text.toString())
        viewModel.getSaleData(jsonObject).observe(this@SaleReportActivity) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    if (response.data!!.data.isNotEmpty()) {
                        binding.rvSaleReportList.layoutManager =
                            LinearLayoutManager(this@SaleReportActivity)
                        binding.rvSaleReportList.isNestedScrollingEnabled = false
                        binding.rvSaleReportList.adapter =
                            SaleReportAdapter(response.data.data, this)
                        binding.progressCircular.visibility = View.GONE
                        binding.rvSaleReportList.visibility = View.VISIBLE

                    } else {
                        Utility.showToast(this@SaleReportActivity, "Data Not Found!")
                        binding.progressCircular.visibility = View.GONE
                    }
                }

                is NetworkResult.Error -> {
                    Utility.showToast(this@SaleReportActivity, response.message.toString())
                }
            }
        }
    }

    override fun onItemClick(position: Int, data: String) {
        val intent = Intent(this@SaleReportActivity, SaleReportDetailActivity::class.java)
        intent.putExtra("LOCATION_CODE", locationCode)
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
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        if (isFromDate) {
            binding.tvTodate.text = sdf.format(cal.getTime())
        } else {
            binding.tvFromDate.text = sdf.format(cal.getTime())
        }

    }

}