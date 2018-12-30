package com.example.q.cs496_1.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.models.Food
import kotlinx.android.synthetic.main.entry_food.view.*

class FoodAdapter(val foodList: ArrayList<Food>, val context: Context): RecyclerView.Adapter<FoodAdapter.Holder>() {
    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder {
        var holder = Holder(LayoutInflater.from(context).inflate(R.layout.entry_food, parent, false))
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(context, foodList[position])
    }

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        val view= view
        fun bind(context: Context, food: Food) {
            view.timeInfo.setText(food.time)
            view.foods.setText(food.food)
        }
    }
}