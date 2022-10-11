package com.optimum.optimumreport.ui.salereport

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.optimum.optimumreport.adapter.SaleReportAdapter
import com.optimum.optimumreport.databinding.FragmentSaleReportBinding
import com.optimum.optimumreport.interfaces.OnInternetCheckListener
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*

class SaleReportFragment : Fragment(), OnItemClickListener {

    lateinit var binding: FragmentSaleReportBinding
    val args: SaleReportFragmentArgs by navArgs()
    lateinit var viewModel: CafePosViewModel
    var locationCode = ""
    var cal = Calendar.getInstance()
    var isFromDate = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.e("TAG", "onCreateView: ")
        binding = FragmentSaleReportBinding.inflate(inflater, container, false)
        binding.include.tvTitle.text = "Sale Report"
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvFromDate.text = sdf.format(cal.getTime())
        binding.tvTodate.text = sdf.format(cal.getTime())


        locationCode = args.locationCode
        viewModel = ViewModelProvider(requireActivity())[CafePosViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("TAG", "onViewCreated: ")

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

                if (Utility.isAppOnLine(requireActivity(), object : OnInternetCheckListener {
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
            requireActivity(),
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
        viewModel.getSaleData(jsonObject).observe(requireActivity()) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    if (isAdded) {
                        if (response.data!!.data.isNotEmpty()) {
                            binding.rvSaleReportList.layoutManager =
                                LinearLayoutManager(requireActivity())
                            binding.rvSaleReportList.isNestedScrollingEnabled = false
                            binding.rvSaleReportList.adapter =
                                SaleReportAdapter(response.data.data, this)
                            binding.progressCircular.visibility = View.GONE
                            binding.rvSaleReportList.visibility = View.VISIBLE

                        } else {
                            Utility.showToast(requireActivity(), "Data Not Found!")
                            binding.progressCircular.visibility = View.GONE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Utility.showToast(requireActivity(), response.message.toString())
                }
            }
        }
    }

    override fun onItemClick(position: Int, data: String) {
        val intent = Intent(requireActivity(), SaleReportDetailActivity::class.java)
        intent.putExtra("LOCATION_CODE", locationCode)
        intent.putExtra("BILL_CODE", data)
        requireActivity().startActivity(intent)

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