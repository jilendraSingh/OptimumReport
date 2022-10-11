package com.optimum.optimumreport.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.adapter.OutLetAdapter
import com.optimum.optimumreport.adapter.ReportListAdapter
import com.optimum.optimumreport.databinding.FragmentOutletLocationBinding
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.model.OutletModel
import com.optimum.optimumreport.model.ReportModel
import com.optimum.optimumreport.utility.NetworkResult
import com.optimum.optimumreport.utility.Utility
import com.optimum.optimumreport.viewmodel.CafePosViewModel

class OutletLocationFragment : Fragment(), OnItemClickListener {

    lateinit var binding: FragmentOutletLocationBinding
    lateinit var viewModel: CafePosViewModel
    var locationCodeData = ""
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOutletLocationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CafePosViewModel::class.java]
        sharedPreferences =
            requireActivity().getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOutLetLocationList()


    }

    private fun getReportType() {

        //val userCode = sharedPreferences.getInt(Constants.USER_CODE, 0)

        viewModel.getReport("2").observe(requireActivity()) { response ->

            when (response) {
                is NetworkResult.Success -> {

                    setUpReportData(response.data)
                }

                is NetworkResult.Error -> {
                    Utility.showToast(requireActivity(), response.message.toString())
                }

            }

        }
    }

    private fun getOutLetLocationList() {
      //  val userCode = sharedPreferences.getInt(Constants.USER_CODE, 0)
        viewModel.getOutletLocationList(2).observe(requireActivity()) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    setUpData(response.data)
                    getReportType()
                }
                is NetworkResult.Error -> {
                    Utility.showToast(requireActivity(), response.message.toString())
                }
            }
        }
    }

    private fun setUpData(data: OutletModel?) {
        try{
            if (isAdded) {
                binding.rvOutletList.layoutManager =
                    LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
                binding.rvOutletList.isNestedScrollingEnabled = true
                binding.rvOutletList.adapter = OutLetAdapter(data?.data!!.locations, this)
            }
        }catch (e:Exception){
            Toast.makeText(requireActivity(),e.printStackTrace().toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun setUpReportData(data: ReportModel?) {
        if (isAdded) {
            binding.rvReportTypeList.layoutManager =
                LinearLayoutManager(requireActivity())
            binding.rvReportTypeList.isNestedScrollingEnabled = true
            binding.rvReportTypeList.adapter =
                ReportListAdapter(requireActivity(), data!!.data, locationCodeData)
        }
    }

    override fun onItemClick(position: Int, locationCode: String) {
        locationCodeData = locationCode
    }
}