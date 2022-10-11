package com.optimum.optimumreport.ui.stockreport


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.optimum.optimumreport.databinding.ActivityStockReportBinding
import com.optimum.optimumreport.interfaces.OnInternetCheckListener
import com.optimum.optimumreport.model.DishHeadModel
import com.optimum.optimumreport.model.StockSelectItemModel
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StockReportActivity : AppCompatActivity() {

    lateinit var binding: ActivityStockReportBinding
    lateinit var viewModel: CafePosViewModel
    var cal = Calendar.getInstance()
    var isFromDate = false
    val dishheadArray=ArrayList<String>()
    val dishheadCodeArray=ArrayList<Int>()
    var dishHeadCode=0

    val rawDescription=ArrayList<String>()
    val rawCode=ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                Log.i("CafeReport", "onItemSelected: "+ dishheadCodeArray[position])
                if(dishheadCodeArray.size!=0){
                    dishHeadCode=dishheadCodeArray[position]
                    if (Utility.isAppOnLine(this@StockReportActivity, object : OnInternetCheckListener {
                            override fun onInternetAvailable() {
                                getItems(dishHeadCode)
                            }
                        })) {
                        getItems(dishHeadCode)
                    }
                }

            }

        }

        binding.spItems.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(rawCode.size!=0){
                    Log.i("CafeReport", "rawCode: ="+ rawCode[position])
                }
            }
        }

        /*if (Utility.isAppOnLine(this@StockReportActivity, object : OnInternetCheckListener {
                override fun onInternetAvailable() {
                    getItems(dishHeadCode)
                }
            })) {
            getItems(dishHeadCode)
        }*/


    }

    private fun getItems(dishHeadCode: Int) {
        val jsonObject= JsonObject()
        jsonObject.addProperty("dishHeadCode", dishHeadCode)
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
        rawCode.clear()
        for(item in data.data){
            rawDescription.add(item.rawDesc)
            rawCode.add(item.rawCode)
        }
        val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, rawDescription as List<Any?>)
        aa.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        binding.spItems.setAdapter(aa)
    }

    private fun getDishHead() {
        viewModel.getDishHead().observe(this@StockReportActivity) { response ->
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
        for(item in data.data){
            dishheadArray.add(item.dishHeadName)
            dishheadCodeArray.add(item.dishHeadCode)
        }
        val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dishheadArray as List<Any?>)
        aa.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
        binding.spDishHead.setAdapter(aa)
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


}