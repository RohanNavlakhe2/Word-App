package com.example.wordapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        GlobalScope.launch{
            m1()
            val result = doSomething()
            val finalResult = result.await()
            m2()

        }






    }

    fun doSomething() = GlobalScope.async {
        delay(2000)
        10
    }

    suspend fun m1(){
        delay(2000)
        println("m1")
    }

    suspend fun m2(){
        delay(3000)
        println("m2")
    }
}