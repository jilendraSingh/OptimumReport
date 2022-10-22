package com.optimum.optimumreport.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.optimum.optimumreport.R


class CustomSpinnerAdapter(val context: Context, val listOfItem: List<String>, val title: String) :
    BaseAdapter() {

    override fun getCount(): Int {
        return listOfItem.size + 1
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val view = (LayoutInflater.from(context)).inflate(R.layout.custom_spinner_items, null)
        val textview: TextView = view.findViewById(R.id.tv_title) as TextView
        if (position == 0) {
            textview.text = title
        } else {
            textview.text = listOfItem[position-1]
        }
        return view
    }
}