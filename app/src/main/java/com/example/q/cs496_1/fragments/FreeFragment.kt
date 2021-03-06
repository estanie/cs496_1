package com.example.q.cs496_1.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.FoodAdapter
import com.example.q.cs496_1.models.Food
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_free.view.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class FreeFragment: Fragment() {
    val CONNECTION_TIMEOUT_MILLISECONDS = 60000
    private var adapter: FoodAdapter? = null
    var foodList: ArrayList<Food>? = null
    val url = "http://api.epthy.com:5000/food/"
    val simpleDate = SimpleDateFormat("yyyy-MM-dd")
    var date = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_free, container, false)
        if (date == "") date = simpleDate.format(Calendar.getInstance().time)
        view!!.date.setText(convertDate(date))
        if (foodList == null) {
            view.progress.visibility = View.VISIBLE
            GetFoodAsyncTask().execute(url)
        }
        else {
            adapter = FoodAdapter(foodList!!, context!!)
            view!!.foodList.adapter = adapter
            view!!.foodList.layoutManager = LinearLayoutManager(context)
            view!!.foodList.visibility = View.VISIBLE
            view!!.progress.visibility = View.INVISIBLE
        }

        val current = Calendar.getInstance()
        view!!.date.setOnClickListener {
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener{ dpview, year, month, dayOfMonth ->
                date = ""+year+"-"
                if (month < 10) date+= "0"
                date+=""+(month+1) + "-"
                if (dayOfMonth < 10) date += "0"
                date += dayOfMonth
                view!!.date.setText(convertDate(date))
                view.progress.visibility = View.VISIBLE
                view.foodList.visibility = View.INVISIBLE
                GetFoodAsyncTask().execute(url)
            }, current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }

        return view
    }

    inner class GetFoodAsyncTask: AsyncTask<String, String, String>() {
        override fun doInBackground(vararg urls: String?): String {
            var urlConnection: HttpURLConnection? = null
            try {
                val url = URL(urls[0]+date)
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = CONNECTION_TIMEOUT_MILLISECONDS
                var inString = streamToString(urlConnection.inputStream)

                publishProgress(inString)
            } catch(ex:Exception) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }
            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val gson = GsonBuilder().create()
                val json: String = gson.fromJson(values[0], object : TypeToken<String>() {}.type)
                Log.e("TEST", json)
                foodList = gson.fromJson(json, object : TypeToken<List<Food>>() {}.type)
                adapter = FoodAdapter(foodList!!, context!!)
                view!!.foodList.adapter = adapter
                view!!.foodList.layoutManager = LinearLayoutManager(context)
                view!!.foodList.visibility = View.VISIBLE
                view!!.progress.visibility = View.INVISIBLE
            } catch(ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun convertDate(date: String) : String {
        var str = ""
        var cnt = 0
        for (i in 0..(date.length-1)) {
            if (date[i] != '-') {
                str += date[i]
            }
            else {
                if (cnt == 0) str+="년 "
                else if (cnt == 1) str+="월 "
                cnt++
            }
        }
        str+= "일"
        return str
    }
    // TODO(@estanie): Changes to Helper..
    private fun streamToString(inputStream: InputStream): String {
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""
        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result+=line
                }
            } while (line != null)
            inputStream.close()
        } catch(ex: Exception) {
            ex.printStackTrace()
        }
        return result
    }
}