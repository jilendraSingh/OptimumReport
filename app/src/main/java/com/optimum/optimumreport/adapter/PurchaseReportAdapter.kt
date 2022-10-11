package com.optimum.optimumreport.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.databinding.SaleReportAdapterLayoutBinding
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.model.PurchaseReportModel

class PurchaseReportAdapter(
    val listOfSaleData: List<PurchaseReportModel.Data>,
    val listener: OnItemClickListener
) : RecyclerView.Adapter<PurchaseReportAdapter.MyViewHolder>() {
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
        val item: PurchaseReportModel.Data = listOfSaleData[position]
        holder.binding.tvDate.text = item.sBlDate
        holder.binding.tvTime.text = item.bldate
        holder.binding.tvBillAmount.text = item.blno.toString()
        holder.binding.tvBillNo.text = item.billamt.toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(position, item.blno.toString())
        }
    }

    override fun getItemCount(): Int {
        return listOfSaleData.size
    }


}