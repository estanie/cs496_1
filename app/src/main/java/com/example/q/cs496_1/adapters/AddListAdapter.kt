package com.example.q.cs496_1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.q.cs496_1.R
import com.example.q.cs496_1.models.Address

class AddListAdapter (val context: Context, val addList: ArrayList<Address>) : BaseAdapter() {
    private class ViewHolder{
        var addPhoto : ImageView? = null
        var addName : TextView? = null
        var addNumber : TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder : ViewHolder

        if (convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.address_entry, null)
            holder = ViewHolder()
            holder.addPhoto = view.findViewById(R.id.addPhoto)
            holder.addName = view.findViewById(R.id.addName)
            holder.addNumber = view.findViewById(R.id.addNumber)

            view.tag = holder
        }else{
            holder = convertView.tag as ViewHolder
            view = convertView
        }

        val address = addList[position]

        val resourceId = context.resources.getIdentifier(address.photo, "drawable", context.packageName)
        holder.addPhoto?.setImageResource(resourceId)
        holder.addName?.text = address.name
        holder.addNumber?.text = address.number

        return view
    }

    override fun getItem(position: Int): Any {
        return addList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return addList.size
    }
}