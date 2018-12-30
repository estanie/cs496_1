package com.example.q.cs496_1.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.address_entry.*
import com.example.q.cs496_1.R


class AddressEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_edit)

        if (intent.hasExtra("name")) {
            addName.text = intent.getStringExtra("name")
            addNumber.text = intent.getStringExtra("number")
        }
    }
}