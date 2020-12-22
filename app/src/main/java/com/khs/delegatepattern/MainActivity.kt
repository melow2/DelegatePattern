package com.khs.delegatepattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    //https://beomseok95.tistory.com/249?category=1064946
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstOffset = FirstOffset()
        val example1 = Example1(firstOffset)

        val secondOffset = SecondOffset()
        val example2 = Example2(secondOffset)

        Log.d("DEBUG","[Example1] - width:${example1.getWidth()}, height:${example1.getHeight()}")
        Log.d("DEBUG","[Example2] - width:${example2.getWidth()}, height:${example2.getHeight()}")

        val evenLength = compose(::isEven,::length)
        val strs = listOf("atongmon","antongmon","tongtmon","tistory","tastory")
        Log.d("DEBUG","${strs.filter(evenLength)}")
        val temp1 = strs.map(String::length)
        reperenceConstuctorTest()
    }

    class Foo
    fun function(factory: () -> Foo) {
        val x: Foo = factory()
        val temp = x
    }
    fun reperenceConstuctorTest(){
        function(::Foo)
    }

}