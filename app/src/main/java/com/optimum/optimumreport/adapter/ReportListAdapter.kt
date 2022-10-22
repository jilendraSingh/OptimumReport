package com.optimum.optimumreport.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.MainActivity.Companion.locationCodeData
import com.optimum.optimumreport.databinding.ReportListItemBinding
import com.optimum.optimumreport.model.ReportModel
import com.optimum.optimumreport.ui.purchasereport.PurchaseReportActivity
import com.optimum.optimumreport.ui.salereport.SaleReportActivity
import com.optimum.optimumreport.ui.stockreport.StockReportActivity

class ReportListAdapter(val context: Context, val data: List<ReportModel.Data>) :
    RecyclerView.Adapter<ReportListAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ReportListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ReportListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvReportName.text = data.get(position).reportName
        holder.itemView.setOnClickListener {
            if (data.get(position).reportCode == 1) {
                Log.e("TAG", "locationCodeData: " + locationCodeData)
                if (locationCodeData != "") {
                    val intent = Intent(context, SaleReportActivity::class.java)
                    intent.putExtra("LOCATION_CODE", locationCodeData)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Please Select Outlet Location !", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            if (data.get(position).reportCode == 2) {
                if (locationCodeData != "") {
                    val intent = Intent(context, PurchaseReportActivity::class.java)
                    intent.putExtra("LOCATION_CODE", locationCodeData)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Please Select Outlet Location !", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            if (data.get(position).reportCode == 3) {
                if (locationCodeData != "") {
                    val intent = Intent(context, StockReportActivity::class.java)
                    intent.putExtra("LOCATION_CODE", locationCodeData)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Please Select Outlet Location !", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}