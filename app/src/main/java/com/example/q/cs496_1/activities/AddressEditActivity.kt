package com.example.q.cs496_1.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.address_entry.*
import com.example.q.cs496_1.R
import kotlinx.android.synthetic.main.address_edit.*


class AddressEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_edit)

        if (intent.hasExtra("photo")) addEditPhoto.setImageBitmap(intent.getParcelableExtra<Bitmap>("photo"))
        if (intent.hasExtra("name")) addEditName.setText(intent.getStringExtra("name"))
        if (intent.hasExtra("number")) addEditNumber.setText(intent.getStringExtra("number"))
        //if (intent.hasExtra("email")) addEmail.text = intent.getStringExtra("email")
    }
}