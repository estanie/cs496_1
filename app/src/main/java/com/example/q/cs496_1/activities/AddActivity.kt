package com.example.q.cs496_1.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.AddListAdapter
import com.example.q.cs496_1.models.Address
import kotlinx.android.synthetic.main.address_main.*

class AddActivity: AppCompatActivity() {
    var addList = arrayListOf<Address>(
        Address("Chow Chow", "010-XXXX-XXXX", "photo00", "memo0"),
        Address("Breed Pomeranian", "010-XXXX-XXXX", "photo01", "memo1"),
        Address("Golden Retriver", "010-XXXX-XXXX", "photo02", "memo2"),
        Address("Yorkshire Terrier", "010-XXXX-XXXX", "photo03", "memo3"),
        Address("Pug", "010-XXXX-XXXX", "photo04", "memo4"),
        Address("Alaskan Malamute", "010-XXXX-XXXX", "photo05", "memo5"),
        Address("Chow Chow", "010-XXXX-XXXX", "photo00", "memo0"),
        Address("Breed Pomeranian", "010-XXXX-XXXX", "photo01", "memo1"),
        Address("Golden Retriver", "010-XXXX-XXXX", "photo02", "memo2"),
        Address("Yorkshire Terrier", "010-XXXX-XXXX", "photo03", "memo3"),
        Address("Pug", "010-XXXX-XXXX", "photo04", "memo4"),
        Address("Alaskan Malamute", "010-XXXX-XXXX", "photo05", "memo5")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_main)

        val addAdapter = AddListAdapter(this, addList)
        addListView.adapter = addAdapter
    }
}