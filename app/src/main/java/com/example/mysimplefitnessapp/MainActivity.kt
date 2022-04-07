package com.example.mysimplefitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import splitties.alertdialog.alertDialog
import splitties.alertdialog.okButton
import splitties.toast.longToast
import splitties.toast.toast


class MainActivity : AppCompatActivity() {

    //Log-Variable und Konstante
    private val TAG = "MainActivity"
    private val mURL = "https://draussenwetter.appspot.com/MCdata.json"

    //Layout-Variable
    private val btnLoad : Button by lazy{ findViewById(R.id.loadDataButton) }
    private val tvPuls : TextView by lazy{ findViewById(R.id.textViewPuls) }

    //Variablen
    private val mRequestQueue : RequestQueue by lazy { Volley.newRequestQueue(applicationContext) }
    private val mHandler : Handler by lazy { Handler() }
    private lateinit var mRunnable : Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var loading = false


        btnLoad.setOnClickListener {
            Log.i(TAG, "Button LadeDaten wurde gedrückt")
            loading = !loading
            btnLoad.text = if (loading) getString(R.string.stop_data)
            else getString(R.string.load_data)
            liveData(loading)
        }
    }

    private fun liveData(loading : Boolean) {
        if (loading) {
            mRunnable = Runnable {
                mHandler.postDelayed(mRunnable, 3 * 1000)
                getDataFromInternet()
            }
            mHandler.postDelayed(mRunnable, 100)
        } else {
            mHandler.removeCallbacks(mRunnable)
        }
    }

    private fun getDataFromInternet() {
        val mStringRequest = StringRequest(
            Request.Method.GET, mURL,
            {
                // longToast(getString(R.string.success_response, it.toString()))
                parseJSONData(it)
            }, Response.ErrorListener {
                dialogError(getString(R.string.error_internet_communication))
            })

        mRequestQueue.add(mStringRequest)
    }

    private fun parseJSONData(jsonString : String) {
        try {
            //response String zu einem JSON Objekt
            val obj = JSONObject(jsonString)
            val pulse = obj.getInt("puls")

            toast("Puls: $pulse")
            tvPuls.text = getString(R.string.pulse) + pulse.toString()
        } catch (e : JSONException) {
            e.printStackTrace()
            Log.e(TAG, getString(R.string.error_json_parsing))
            dialogError(getString(R.string.error_json_parsing))
        }
    }
    private fun dialogError(message : String) {
        alertDialog(title = getString(R.string.error_msg_title), message = message) {
            okButton {
                finish()
            }
        }.show()
    }

}