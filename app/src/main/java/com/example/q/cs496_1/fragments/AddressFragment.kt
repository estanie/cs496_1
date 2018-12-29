package com.example.q.cs496_1.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.AddListAdapter
import com.example.q.cs496_1.models.Address
import kotlinx.android.synthetic.main.fragment_address.*
import kotlinx.android.synthetic.main.address_entry.view.*


class AddressFragment: Fragment(){
    var adapter : AddListAdapter? = null
    var addList = arrayListOf<Address>(
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = AddListAdapter(context, addList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_address, container, false)
    } //이해 못하는 부분

    override fun onStart() {
        super.onStart()
        var context = getActivity() as Context
        adapter = AddListAdapter(context,addList)
        addRecyclerView.adapter = adapter
        val lm = LinearLayoutManager(context)
        addRecyclerView.layoutManager = lm
        addRecyclerView.setHasFixedSize(true)
        // TODO(@quark325): Add data, Save data
    }
}