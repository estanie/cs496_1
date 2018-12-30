package com.example.q.cs496_1.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.q.cs496_1.R
import com.example.q.cs496_1.activities.AddressEditActivity
import com.example.q.cs496_1.models.Address
import kotlinx.android.synthetic.main.address_entry.view.*

class AddListAdapter (val context: Context, val addList: ArrayList<Address>) :
    RecyclerView.Adapter<AddListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder{
        val view = LayoutInflater.from(context).inflate(R.layout.address_entry, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int {
        return addList.size
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(addList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val addEntry = itemView?.findViewById<android.support.constraint.ConstraintLayout>(R.id.addEntry)
        val addPhoto = itemView?.findViewById<ImageView>(R.id.addPhoto)
        val addName = itemView?.findViewById<TextView>(R.id.addName)
        val addNumber = itemView?.findViewById<TextView>(R.id.addNumber)
        fun bind (add: Address, context: Context) {
            if (add.photo != null) {
                addPhoto?.setImageBitmap(add.photo)
            }else{
                addPhoto?.setImageResource(R.mipmap.ic_launcher)
            }
            itemView.addNumber.text=add.number
            itemView.addName.text=add.name

            addEntry?.setOnClickListener{
                val intent = Intent(context, AddressEditActivity::class.java)
                if (add.photo != null) intent.putExtra("photo",add.photo)
                intent.putExtra("name",add.name)
                intent.putExtra("number",add.number)
                intent.putExtra("email",add.email)
                context.startActivity(intent)
            }
        }
    }
}