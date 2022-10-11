package com.optimum.optimumreport.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.databinding.ReportListItemBinding
import com.optimum.optimumreport.model.ReportModel
import com.optimum.optimumreport.ui.purchasereport.PurchaseReportActivity
import com.optimum.optimumreport.ui.salereport.SaleReportActivity
import com.optimum.optimumreport.ui.stockreport.StockReportActivity

class ReportListAdapter(val context:Context,val data: List<ReportModel.Data>, val locationCodeData:String) :RecyclerView.Adapter<ReportListAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ReportListItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ReportListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvReportName.text= data.get(position).reportName
        holder.itemView.setOnClickListener{
            if(data.get(position).reportCode==1){
                val intent = Intent(context, SaleReportActivity::class.java)
                intent.putExtra("LOCATION_CODE", locationCodeData)
                context.startActivity(intent)
            }

            if(data.get(position).reportCode==2){
                val intent = Intent(context, PurchaseReportActivity::class.java)
                intent.putExtra("LOCATION_CODE", locationCodeData)
                context.startActivity(intent)
            }

            if(data.get(position).reportCode==3){
                val intent = Intent(context, StockReportActivity::class.java)
                intent.putExtra("LOCATION_CODE", locationCodeData)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}