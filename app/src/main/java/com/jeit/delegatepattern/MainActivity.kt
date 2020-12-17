package com.jeit.delegatepattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstOffset = FirstOffset()
        val example1 = Example1(firstOffset)

        val secondOffset = SecondOffset()
        val example2 = Example2(secondOffset)

        Log.d("DEBUG","[Example1] - width:${example1.getWidth()}, height:${example1.getHeight()}")
        Log.d("DEBUG","[Example2] - width:${example2.getWidth()}, height:${example2.getHeight()}")
    }
}