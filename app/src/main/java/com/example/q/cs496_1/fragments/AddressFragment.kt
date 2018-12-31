package com.example.q.cs496_1.fragments

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.AddListAdapter
import com.example.q.cs496_1.models.Address
import kotlinx.android.synthetic.main.fragment_address.*
import java.io.BufferedInputStream
import java.util.*


class AddressFragment: Fragment(){
    var adapter : AddListAdapter? = null
    var addList = arrayListOf<Address>(
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = AddListAdapter(context, addList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.fragment_address, container, false)
        return view
    }


    override fun onStart() {
        super.onStart()
        var context = getActivity() as Context
        addList = arrayListOf<Address>()
        getContacts()
        val lm = LinearLayoutManager(context)
        addRecyclerView.layoutManager = lm
        addRecyclerView.setHasFixedSize(true)

        addFab.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE
            if(intent.resolveActivity(context.packageManager)!= null){
                startActivity(intent)
            }
        }
    }
    private fun getContacts() {
        var context = getActivity() as Context
        val adapter = AddListAdapter(context, getContactsData())
        addRecyclerView.adapter = adapter
    }

    private fun getContactsData(): ArrayList<Address> {
        var context = getActivity() as Context
        val addressCursor =
            context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        if ((addressCursor?.count ?: 0) > 0) {
            while (addressCursor != null && addressCursor.moveToNext()) {
                val rowID = addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.Contacts._ID))
                val lookupKey = addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                val name =
                    addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                var number = ""
                if (addressCursor.getInt(addressCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {

                    val numberCursor = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf<String>(rowID),
                        null
                    )
                    while (numberCursor.moveToNext()) {

                        number += numberCursor.getString(
                            numberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        ) + "\n"
                    }
                    numberCursor.close()
                }

                var email = ""
                val emailCursor = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf<String>(rowID),
                    null
                )
                while (emailCursor.moveToNext()) {

                    email += emailCursor.getString(
                        emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    ) + "\n"
                }
                emailCursor.close()

                val contactPhotoUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, rowID)
                val photoStream =
                    ContactsContract.Contacts.openContactPhotoInputStream(context.contentResolver, contactPhotoUri)
                val buffer = BufferedInputStream(photoStream)
                val addressPhoto = BitmapFactory.decodeStream(buffer)

                Log.e("String", addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.Contacts._ID))+"!!!!A")
                Log.e("String", addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))+"!!!!B")

                val keyword = "content://com.android.contacts/contacts/lookup/" +lookupKey + "/" + rowID
                Log.e("String", keyword+"!!!!C")
                addList.add(Address(name, number, email, addressPhoto, keyword))
            }
        }

        addressCursor.close()

        return addList
    }
}