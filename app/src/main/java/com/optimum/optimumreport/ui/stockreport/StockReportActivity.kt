package com.optimum.optimumreport.ui.stockreport


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.optimum.optimumreport.adapter.CustomSpinnerAdapter
import com.optimum.optimumreport.adapter.StockReportAdapter
import com.optimum.optimumreport.databinding.ActivityStockReportBinding
import com.optimum.optimumreport.interfaces.OnInternetCheckListener
import com.optimum.optimumreport.model.DishHeadModel
import com.optimum.optimumreport.model.StockSelectItemModel
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel
import java.text.SimpleDateFormat
import java.util.*


class StockReportActivity : AppCompatActivity() {

    lateinit var binding: ActivityStockReportBinding
    lateinit var viewModel: CafePosViewModel
    var cal = Calendar.getInstance()
    var isFromDate = false
    val dishheadArray=ArrayList<String>()
    val dishheadCodeArray = ArrayList<Int>()
    var dishHeadCode = ""
    var locationCode = ""

    val rawDescription=ArrayList<String>()
    val rawCodeList=ArrayList<String>()
    var rawCode=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationCode=intent.getStringExtra("LOCATION_CODE").toString()

        binding.include.tvTitle.text = "Stock Report"
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvFromDate.text = sdf.format(cal.getTime())
        binding.tvTodate.text = sdf.format(cal.getTime())

        viewModel = ViewModelProvider(this@StockReportActivity)[CafePosViewModel::class.java]


        binding.tvFromDate.setOnClickListener {
            isFromDate = false
            showDatePicker()
        }
        binding.tvTodate.setOnClickListener {
            isFromDate = true
            showDatePicker()
        }
        if (Utility.isAppOnLine(this@StockReportActivity, object : OnInternetCheckListener {
                override fun onInternetAvailable() {
                    getDishHead();
                }
            })) {
            getDishHead();
        }


        binding.spDishHead.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position == 0) {
                    dishHeadCode = "please select dishhead"
                    return
                }
                Log.i("CafeReport", "onItemSelected: " + dishheadCodeArray[position])
                dishHeadCode = dishheadCodeArray[position-1].toString()
                if (Utility.isAppOnLine(this@StockReportActivity, object : OnInternetCheckListener {
                        override fun onInternetAvailable() {
                            getItems(dishHeadCode)
                        }
                    })) {
                    getItems(dishHeadCode)
                }
            }

        }

        binding.spItems.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    rawCode = "please select item"
                    return
                }
                Log.i("CafeReport", "rawCode: =" + rawCodeList[position-1])
                rawCode = rawCodeList[position-1]
            }
        }

        binding.btnSubmit.setOnClickListener {
            if (dishHeadCode.contains("please")) {
                Utility.showToast(this@StockReportActivity, "Please select Dishhead")
                return@setOnClickListener
            }
            if (rawCode.contains("please")) {
                /*Utility.showToast(this@StockReportActivity, "Please select Item")
                return@setOnClickListener*/
                rawCode = "null"
            }


            getStockReport()
        }
    }


    private fun getItems(dishHeadCode: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("dishHeadCode", dishHeadCode)
        jsonObject.addProperty("locationCode", locationCode)
        viewModel.stockItemsList(jsonObject).observe(this@StockReportActivity) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    if (response.data!!.data.isNotEmpty()) {
                        setItems(response.data)
                    } else {
                        Utility.showToast(this@StockReportActivity, "Data Not Found!")
                        binding.progressCircular.visibility = View.GONE
                    }
                }

                is NetworkResult.Error -> {
                    Utility.showToast(this@StockReportActivity, response.message.toString())
                }
            }
        }
    }

    private fun setItems(data: StockSelectItemModel) {
        rawDescription.clear()
        rawCodeList.clear()
        for (item in data.data) {
            rawDescription.add(item.rawDesc)
            rawCodeList.add(item.rawCode)
        }

        val customAdapter =
            CustomSpinnerAdapter(this@StockReportActivity, rawDescription, "Please Select Items")
        binding.spItems.adapter = customAdapter
    }

    private fun getDishHead() {
        viewModel.getDishHead(locationCode.toInt()).observe(this@StockReportActivity) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    if (response.data!!.data.isNotEmpty()) {

                        setDishHeadItems(response.data)
                    } else {
                        Utility.showToast(this@StockReportActivity, "Data Not Found!")
                        binding.progressCircular.visibility = View.GONE
                    }
                }

                is NetworkResult.Error -> {
                    Utility.showToast(this@StockReportActivity, response.message.toString())
                }
            }
        }
    }

    private fun setDishHeadItems(data: DishHeadModel) {
        dishheadArray.clear()
        dishheadCodeArray.clear()
        for (item in data.data) {
            dishheadArray.add(item.dishHeadName)
            dishheadCodeArray.add(item.dishHeadCode)
        }
        val customAdapter =
            CustomSpinnerAdapter(this@StockReportActivity, dishheadArray, "Please Select DishHead")
        binding.spDishHead.adapter = customAdapter
    }

    fun showDatePicker() {

        DatePickerDialog(
            this@StockReportActivity,
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()


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

    private fun getStockReport() {
        val jsonObject= JsonObject()
        jsonObject.addProperty("startDate", binding.tvFromDate.text.toString())
        jsonObject.addProperty("endDate", binding.tvTodate.text.toString())
        jsonObject.addProperty("locationCode", locationCode)
        jsonObject.addProperty("rawCode", rawCode)
        jsonObject.addProperty("dishHeadCode", dishHeadCode)
        jsonObject.addProperty("reportType", 0)


        viewModel.getStockReport(jsonObject).observe(this@StockReportActivity) { response ->
            try {
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data!!.data.isNotEmpty()) {
                            binding.rvStockReportList.layoutManager=LinearLayoutManager(this@StockReportActivity)
                            binding.rvStockReportList.adapter=StockReportAdapter(response.data.data)
                            binding.rvStockReportList.visibility=View.VISIBLE
                        } else {
                            Utility.showToast(this@StockReportActivity, "Data Not Found!")
                            binding.progressCircular.visibility = View.GONE
                            binding.rvStockReportList.visibility=View.GONE
                        }
                    }

                    is NetworkResult.Error -> {
                        Utility.showToast(this@StockReportActivity, response.message.toString())
                    }
                }
            }catch (e:Exception){Utility.showToast(this@StockReportActivity,e.printStackTrace().toString())}

        }
    }


}