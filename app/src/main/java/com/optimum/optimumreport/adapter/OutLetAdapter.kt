package com.optimum.optimumreport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.optimum.optimumreport.R
import com.optimum.optimumreport.databinding.OutletAdapterLayoutBinding
import com.optimum.optimumreport.interfaces.OnItemClickListener
import com.optimum.optimumreport.model.OutletModel

class OutLetAdapter(val context: Context, val listOfLocation: List<OutletModel.Data.Location>, val listener:OnItemClickListener) : RecyclerView.Adapter<OutLetAdapter.MyViewHolder>() {
    var selectionPosition=-1;
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

        holder.binding.cardview.setCardBackgroundColor(context.resources.getColor(R.color.white))
        holder.binding.tvLocationName.setTextColor(ContextCompat.getColor(context, R.color.black));
        holder.binding.tvLocationAddress.setTextColor(ContextCompat.getColor(context, R.color.black));

        //listener.onItemClick(position,listOfLocation[0].locationCode.toString())

        if(selectionPosition==position){
            holder.binding.tvLocationName.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.binding.tvLocationAddress.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.binding.cardview.setCardBackgroundColor(context.resources.getColor(R.color.red_color))
        }

        holder.binding.cardview.setOnClickListener{
            selectionPosition=position
            listener.onItemClick(position,listOfLocation[position].locationCode.toString())
            notifyDataSetChanged()
        }
    }
}