package com.example.nazorat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nazorat.models.Buyurtmalar
import com.example.nazorat.models.Mahsulotlar
import com.example.nazorat.myobjects.MyObjects.buyurtma
import com.example.nazorat.myobjects.MyObjects.buyurtmalarList
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList

class MyView : ViewModel() {
    var mahsulotlarMutableLiveData: MutableLiveData<MutableList<Mahsulotlar>>? = null
    var buyurtmalarMutableLiveData: MutableLiveData<MutableList<Buyurtmalar>>? = null
    var buyurtmaMutableLiveData: MutableLiveData<MutableList<Buyurtmalar>>? = null

    init {
        mahsulotlarMutableLiveData = MutableLiveData()
        buyurtmalarMutableLiveData = MutableLiveData()
        buyurtmaMutableLiveData = MutableLiveData()
        buyurtmalarMutableLiveData!!.value = buyurtmalarList
        mahsulotlarMutableLiveData!!.value = mahsulotlarList
        buyurtmaMutableLiveData!!.value = buyurtma
    }

    fun getMahsulot() : MutableLiveData<MutableList<Mahsulotlar>>? {
        return mahsulotlarMutableLiveData!!
    }

    fun getBuyurtma() : MutableLiveData<MutableList<Buyurtmalar>> ? {
        return buyurtmalarMutableLiveData!!
    }

    fun getIsBuyurtma() : MutableLiveData<MutableList<Buyurtmalar>> ? {
        return buyurtmaMutableLiveData!!

    }
}