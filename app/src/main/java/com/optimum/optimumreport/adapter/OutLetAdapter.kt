package com.optimum.optimumreport.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.databinding.OutletAdapterLayoutBinding
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.model.OutletModel

class OutLetAdapter(val listOfLocation: List<OutletModel.Data.Location>,val listener:OnItemClickListener) : RecyclerView.Adapter<OutLetAdapter.MyViewHolder>() {

    class MyViewHolder(val binding : OutletAdapterLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(OutletAdapterLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return listOfLocation.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvLocationName.text= listOfLocation[position].locationName
        holder.binding.tvLocationAddress.text= listOfLocation[position].address

        listener.onItemClick(position,listOfLocation[0].locationCode.toString())

        holder.itemView.setOnClickListener{
            listener.onItemClick(position,listOfLocation[position].locationCode.toString())
        }
    }
}