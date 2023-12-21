package com.example.nazorat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nazorat.mydb.MyDb
import com.example.nazorat.myobjects.MyObjects.buyurtmalarList
import com.example.nazorat.myobjects.MyObjects.getViewModel
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList

class MainActivity : AppCompatActivity() {
    lateinit var myDb: MyDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}