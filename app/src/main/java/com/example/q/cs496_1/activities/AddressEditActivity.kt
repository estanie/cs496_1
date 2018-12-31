package com.example.q.cs496_1.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.q.cs496_1.R
import kotlinx.android.synthetic.main.address_edit.*




class AddressEditActivity : AppCompatActivity() {

    val REQUEST_CONTACT = 1
    lateinit var contactUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_edit)

        if (intent.hasExtra("photo")) addEditPhoto.setImageBitmap(intent.getParcelableExtra<Bitmap>("photo"))
        if (intent.hasExtra("name")) addEditName.setText(intent.getStringExtra("name"))
        if (intent.hasExtra("number")) addEditNumber.setText(intent.getStringExtra("number"))
        //if (intent.hasExtra("email")) addEmail.text = intent.getStringExtra("email")

        addEdit.setOnClickListener {
            selectContact()
            /*
            intent = Intent(Intent.ACTION_INSERT)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE
            //val addPhoto = addEditPhoto.()
            val addName = addEditName.text
            val addNumber = addEditNumber.text
            intent.putExtra(ContactsContract.Intents.Insert.NAME, addName)
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, addNumber)
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
            */

        }
        addCancel.setOnClickListener{
            viewContact()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK){
            contactUri = data!!.data
            Log.e("String",contactUri.toString() + "!!!!!")
        }
    }

    private fun selectContact(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = ContactsContract.Contacts.CONTENT_TYPE
        if(intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent, REQUEST_CONTACT)
        }
    }
    private fun viewContact(){
        val intent = Intent(Intent.ACTION_VIEW,contactUri)
        if (intent.resolveActivity(packageManager)!= null){
            startActivity(intent)
        }
    }
}