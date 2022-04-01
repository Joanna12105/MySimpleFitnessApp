package com.example.mysimplefitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import splitties.toast.toast


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loadDataB = findViewById(R.id.loadDataButton) as Button

        loadDataB.setOnClickListener {
            toast("Button gedrückt")
        }

    }
}