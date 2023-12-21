package com.example.nazorat.myobjects

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.nazorat.models.Buyurtmalar
import com.example.nazorat.models.Mahsulotlar
import com.example.nazorat.viewmodel.MyView

object MyObjects {
    var mahsulotlarList: MutableList<Mahsulotlar> = mutableListOf()
    var buyurtmalarList: MutableList<Buyurtmalar> = mutableListOf()
    var buyurtma = mutableListOf<Buyurtmalar>()


    fun getViewModel(fragmentActivity: FragmentActivity): MyView {
        return ViewModelProvider(fragmentActivity)[MyView::class.java]
    }
}