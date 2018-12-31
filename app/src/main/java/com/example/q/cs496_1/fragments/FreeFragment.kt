package com.example.q.cs496_1.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Contacts
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.PrecomputedText
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.q.cs496_1.R
import com.example.q.cs496_1.adapters.FoodAdapter
import com.example.q.cs496_1.models.Food
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_free.view.*
import retrofit2.Retrofit
import retrofit2.http.GET
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.*

class FreeFragment: Fragment() {
    val CONNECTION_TIMEOUT_MILLISECONDS = 60000
    private var adapter: FoodAdapter? = null
    var foodList: ArrayList<Food>? = null
    val url = "http://api.epthy.com:5000/food"

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_free, container, false)
        if (foodList == null)
            GetFoodAsyncTask().execute(url)
        else {
            adapter = FoodAdapter(foodList!!, context!!)
            view!!.foodList.adapter = adapter
            view!!.foodList.layoutManager = LinearLayoutManager(context)
        }
        return view
    }

    inner class GetFoodAsyncTask: AsyncTask<String, String, String>() {
        override fun doInBackground(vararg urls: String?): String {
            var urlConnection: HttpURLConnection? = null
            try {
                val url = URL(urls[0]
                )
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
            } catch(ex: Exception) {
                ex.printStackTrace()
            }
        }
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