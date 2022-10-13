package com.optimum.optimumreport


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.adapter.OutLetAdapter
import com.optimum.optimumreport.adapter.ReportListAdapter
import com.optimum.optimumreport.databinding.ActivityMainBinding
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val toolbarTitle = binding.include2.toolbar.findViewById(R.id.tv_title) as TextView
        toolbarTitle.text = "Outlet Location"
        binding.include2.toolbar.inflateMenu(R.menu.optionmenu)
        binding.include2.toolbar.overflowIcon?.setTint(resources.getColor(R.color.white))

        viewModel = ViewModelProvider(this@MainActivity)[CafePosViewModel::class.java]
        sharedPreferences = getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)

        getOutLetLocationList()
        binding.include2.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.logout) {
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            false
        }
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
        try {

            binding.rvOutletList.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            binding.rvOutletList.isNestedScrollingEnabled = true
            binding.rvOutletList.adapter = OutLetAdapter(data?.data!!.locations, this)

        } catch (e: Exception) {
            Log.e("optimum", "setUpData: "+e.printStackTrace() )

        }

    }

    private fun setUpReportData(data: ReportModel?) {

        binding.rvReportTypeList.layoutManager =
            LinearLayoutManager(this@MainActivity)
        binding.rvReportTypeList.isNestedScrollingEnabled = true
        binding.rvReportTypeList.adapter =
            ReportListAdapter(this@MainActivity, data!!.data, locationCodeData)
        binding.reportType.visibility= View.VISIBLE

    }

    override fun onItemClick(position: Int, locationCode: String) {
        locationCodeData = locationCode
    }
}