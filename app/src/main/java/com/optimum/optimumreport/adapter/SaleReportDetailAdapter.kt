package com.optimum.optimumreport.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.databinding.SaleReportDetailLayoutBinding
import com.optimum.optimumreport.model.SaleReportDetailModel

class SaleReportDetailAdapter(val lisOfData: List<SaleReportDetailModel.Data.Detail>) :
    RecyclerView.Adapter<SaleReportDetailAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: SaleReportDetailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            SaleReportDetailLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: SaleReportDetailModel.Data.Detail = lisOfData.get(position)

        holder.binding.tvName.text = item.rawDesc
        holder.binding.tvQuantity.text = item.quantity.toString()
        holder.binding.tvRate.text = item.rate.toString()
        holder.binding.tvAmount.text = item.itemAmount.toString()
    }

    override fun getItemCount(): Int {
        return lisOfData.size
    }
}