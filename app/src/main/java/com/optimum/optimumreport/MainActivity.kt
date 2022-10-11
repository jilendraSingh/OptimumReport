package com.optimum.optimumreport

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.adapter.OutLetAdapter
import com.optimum.optimumreport.adapter.ReportListAdapter
import com.optimum.optimumreport.databinding.ActivityMainBinding
import com.optimum.optimumreport.databinding.FragmentOutletLocationBinding
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.model.OutletModel
import com.optimum.optimumreport.model.ReportModel
import com.optimum.optimumreport.utility.Constants
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel

class MainActivity : AppCompatActivity() , OnItemClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CafePosViewModel
    var locationCodeData = ""
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include2.tvTitle.text="Outlet Location"

        viewModel = ViewModelProvider(this@MainActivity)[CafePosViewModel::class.java]
        sharedPreferences = getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)

        getOutLetLocationList()
    }

    private fun getOutLetLocationList() {
          val userCode = sharedPreferences.getInt(Constants.USER_CODE, 0)
        viewModel.getOutletLocationList(userCode).observe(this@MainActivity) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    setUpData(response.data)
                    getReportType()
                }
                is NetworkResult.Error -> {
                    Utility.showToast(this@MainActivity, response.message.toString())
                }
            }
        }
    }

    private fun getReportType() {

        val userCode = sharedPreferences.getInt(Constants.USER_CODE, 0)

        viewModel.getReport(userCode.toString()).observe(this@MainActivity) { response ->

            when (response) {
                is NetworkResult.Success -> {
                    setUpReportData(response.data)
                }
                is NetworkResult.Error -> {
                    Utility.showToast(this@MainActivity, response.message.toString())
                }

            }

        }
    }

    private fun setUpData(data: OutletModel?) {
        try{

                binding.rvOutletList.layoutManager =
                    LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
                binding.rvOutletList.isNestedScrollingEnabled = true
                binding.rvOutletList.adapter = OutLetAdapter(data?.data!!.locations, this)

        }catch (e:Exception){
            Toast.makeText(this@MainActivity,e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun setUpReportData(data: ReportModel?) {

            binding.rvReportTypeList.layoutManager =
                LinearLayoutManager(this@MainActivity)
            binding.rvReportTypeList.isNestedScrollingEnabled = true
            binding.rvReportTypeList.adapter =
                ReportListAdapter(this@MainActivity, data!!.data, locationCodeData)

    }

    override fun onItemClick(position: Int, locationCode: String) {
        locationCodeData = locationCode
    }
}