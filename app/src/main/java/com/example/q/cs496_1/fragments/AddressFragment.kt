package com.example.q.cs496_1.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.activities.AddressEditActivity
import com.example.q.cs496_1.adapters.AddListAdapter
import com.example.q.cs496_1.models.Address
import kotlinx.android.synthetic.main.fragment_address.*


class AddressFragment: Fragment(){
    var adapter : AddListAdapter? = null
    var addList = arrayListOf<Address>(
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = AddListAdapter(context, addList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context2 = getActivity() as Context
        val phones = context2.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        while (phones!!.moveToNext()) {
            var photo = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO))
            //TODO(@estanie): Photo does't work
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            val address = Address(name,number)
            address.photo = photo
            addList.add(address)
        }
        phones.close()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.fragment_address, container, false)

        return view
    } //이해 못하는 부분

    override fun onStart() {
        super.onStart()
        //show pragment
        var context = getActivity() as Context
        adapter = AddListAdapter(context,addList)
        addRecyclerView.adapter = adapter
        val lm = LinearLayoutManager(context)
        addRecyclerView.layoutManager = lm
        addRecyclerView.setHasFixedSize(true)


        addFab.setOnClickListener{
            val intent = Intent(context, AddressEditActivity::class.java)
            startActivity(intent)
        }
        // TODO(@quark325): Add data, Save data
    }


}