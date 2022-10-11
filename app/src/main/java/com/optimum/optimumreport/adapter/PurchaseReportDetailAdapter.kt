package com.optimum.optimumreport.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.databinding.SaleReportDetailLayoutBinding
import com.optimum.optimumreport.model.PurchaseReportDetailModel

class PurchaseReportDetailAdapter(val lisOfData: List<PurchaseReportDetailModel.Data.Detail>) :
    RecyclerView.Adapter<PurchaseReportDetailAdapter.MyViewHolder>() {
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
        val item: PurchaseReportDetailModel.Data.Detail = lisOfData.get(position)

        holder.binding.tvName.text = item.rawDesc
        holder.binding.tvQuantity.text = item.qty.toString()
        holder.binding.tvRate.text = item.purcrate.toString()
        holder.binding.tvAmount.text = item.amount.toString()
    }

    override fun getItemCount(): Int {
        return lisOfData.size
    }
}