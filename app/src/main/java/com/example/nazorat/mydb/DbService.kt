package com.example.nazorat.mydb

import com.example.nazorat.models.Buyurtmalar
import com.example.nazorat.models.Mahsulotlar

interface DbService {
    fun addMahsulot(mahsulotlar: Mahsulotlar)

    fun getMahsulot() : MutableList<Mahsulotlar>

    fun updateMahsulot(mahsulotlar: Mahsulotlar)

    fun deleteMahsulot(mahsulotlar: Mahsulotlar)

    fun addBuyurtma(buyurtmalar: Buyurtmalar)

    fun getBuyurtma() : MutableList<Buyurtmalar>

    fun updateBuyurtma(buyurtmalar: Buyurtmalar)

    fun deleteBuyurtma(buyurtmalar: Buyurtmalar)
}