package com.example.q.cs496_1.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.FoodAdapter
import com.example.q.cs496_1.models.Food
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_free.view.*

class FreeFragment: Fragment(){
    private var adapter: FoodAdapter? = null

    var foodList: ArrayList<Food>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.fragment_free, container, false)
        foodList = getTodaysFood()
        adapter = FoodAdapter(foodList!!, context!!)
        view.foodList.adapter = adapter
        return view
    }

    private fun getTodaysFood() : ArrayList<Food> {
        var foodList = ArrayList<Food>()
        // val json = [{"time": "\uc870\uc2dd", "food": ""}, {"time": "\uc911\uc2dd", "food": "\uae30\uc7a5\ubc25\r\n\ucf69\ub098\ubb3c\uad6d\r\n\ub5a1\ubd88\uace0\uae30\r\n\uac10\uc790\ucc44\ubcf6\uc74c\r\n\ubaa9\uc774\ubc84\uc12f\ucd08\ubb34\uce68\r\n\ubc30\ucd94\uac89\uc808\uc774\r\n\uc591\uc0c1\ucd94\uc0d0\ub7ec\ub4dc\r\n\uc728\ubb34\ucc28\n"}, {"time": "\uc11d\uc2dd", "food": "\uc300 \ubc25\r\n\ub3fc\uc9c0\uace0\uae30\uae40\uce58\ucc0c\uac1c\r\n\ud574\ubb3c\ud30c\uc804\r\n\uc7a1\ucc44\uc5b4\ubb35\ubcf6\uc74c\r\n\uc0c1\ucd94\uac89\uc808\uc774\r\n\uae4d\ub450\uae30\r\n\uc591\uc0c1\ucd94\uc0d0\ub7ec\ub4dc\n"}]
        // val gson: Gson()
        // TODO(@estanie): Implement Here.
        return foodList
    }
}