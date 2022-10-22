package com.optimum.optimumreport.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.databinding.SaleReportAdapterLayoutBinding
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.model.SaleReportModel

class SaleReportAdapter(val listOfSaleData: List<SaleReportModel.Data>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<SaleReportAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: SaleReportAdapterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        return MyViewHolder(
            SaleReportAdapterLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: SaleReportModel.Data = listOfSaleData[position]
        holder.binding.tvDate.text = item.sBillDate + " " + item.billTime
        holder.binding.tvAccount.text = item.accName
        holder.binding.tvBillAmount.text = item.billAmount.toString()
        holder.binding.tvBillNo.text = (item.docNo.toInt()).toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(position, item.billCode.toString())
        }
    }

    override fun getItemCount(): Int {
        return listOfSaleData.size
    }
}