package com.optimum.optimumreport.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.databinding.StockReportAdapterItemBinding
import com.optimum.optimumreport.model.StockReportModel

class StockReportAdapter(val data: List<StockReportModel.Data>) : RecyclerView.Adapter<StockReportAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: StockReportAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            StockReportAdapterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvItemName.text= data[position].rawDesc
        holder.binding.tvOpening.text= data[position].opening.toString()
        holder.binding.tvInward.text= data[position].inward.toString()
        holder.binding.tvOutward.text= data[position].outward.toString()
        holder.binding.tvClosing.text= data[position].closing.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }


}