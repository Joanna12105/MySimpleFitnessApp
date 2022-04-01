package com.example.mysimplefitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import splitties.toast.longToast
import splitties.toast.toast


class MainActivity : AppCompatActivity() {

    //Log-Variable und Konstante
    private val TAG = "MainActivity"
    private val mURL = "https://run.mocky.io/v3/e815e607-a1ba-4083-ae67-895c6078d4bb"

    //Layout-Variable
    private val btnLoad : Button by lazy{ findViewById(R.id.loadDataButton) }

    //Variablen
    private val mRequestQueue : RequestQueue by lazy { Volley.newRequestQueue(applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnLoad.setOnClickListener {
            Log.i(TAG, "Button LadeDaten wurde gedrückt")
            getDataFromInternet()
        }
    }

    private fun getDataFromInternet() {
        val mStringRequest = StringRequest(
            Request.Method.GET, mURL,
            Response.Listener {
                longToast(getString(R.string.success_response, it.toString()))
            }, Response.ErrorListener {
                Log.i(TAG, getString(R.string.error_response, it.toString()))
            })

        mRequestQueue.add(mStringRequest)
    }
}