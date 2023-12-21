package com.example.nazorat.mydb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.nazorat.models.Buyurtmalar
import com.example.nazorat.models.Mahsulotlar
import java.io.ByteArrayOutputStream

class MyDb(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), DbService {

    companion object {
        const val DB_NAME = "mydb"
        const val DB_VERSION = 1

        const val TABLE_BUYURTMA = "buyurtmalar"
        const val ID = "id"
        const val BUYURTMA_NARXI = "Bnarxi"
        const val BUYURTMA_MIQDORI = "Bmiqdor"
        const val BUYURTMA_NOMI = "Bnomi"
        const val BUYURTMA_RANGI = "Brangi"
        const val IMG = "img"


        const val TABLE_MAHSULOT = "mahsulotlar"
        const val MAHSULOT_NOMI = "Mnomi"
        const val MAHSULOT_NARXI = "Mnarxi"
        const val MAHSULOT_OLCHAMI = "Molchami"
        const val MAHSULOT_RANGI = "Mrangi"
        const val MAHSULOT_FASLI = "Mfasli"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryMahsulotlar = "CREATE TABLE $TABLE_MAHSULOT($ID integer not null primary key, $MAHSULOT_NOMI text not null, $MAHSULOT_NARXI text not null, " +
                "$MAHSULOT_OLCHAMI text not null, $MAHSULOT_RANGI text not null, $MAHSULOT_FASLI text not null, $IMG BLOB)"
        val queryBuyurtmalar = "CREATE TABLE $TABLE_BUYURTMA($ID integer not null primary key, $BUYURTMA_NOMI text not null, $BUYURTMA_NARXI text not null, " +
                "$BUYURTMA_MIQDORI text not null, $BUYURTMA_RANGI text not null, $IMG BLOB)"
        db?.execSQL(queryMahsulotlar)
        db?.execSQL(queryBuyurtmalar)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    override fun addMahsulot(mahsulotlar: Mahsulotlar) {
        val contentValues = ContentValues()
        contentValues.put(MAHSULOT_FASLI, mahsulotlar.fasli)
        contentValues.put(MAHSULOT_NARXI, mahsulotlar.narxi)
        contentValues.put(MAHSULOT_NOMI, mahsulotlar.nomi)
        contentValues.put(MAHSULOT_RANGI, mahsulotlar.rangi)
        contentValues.put(MAHSULOT_OLCHAMI, mahsulotlar.olchami)
        val byteArrayOutputStream = ByteArrayOutputStream()
        mahsulotlar.rasm!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val rasm = byteArrayOutputStream.toByteArray()
        contentValues.put(IMG, rasm)

        this.writableDatabase.insert(TABLE_MAHSULOT, null, contentValues)
    }

    override fun getMahsulot(): MutableList<Mahsulotlar> {
        val mahsulotlarList = mutableListOf<Mahsulotlar>()

        val cursor = this.readableDatabase.rawQuery("SELECT * FROM $TABLE_MAHSULOT", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val nomi = cursor.getString(1)
                val narxi = cursor.getString(2)
                val olchami = cursor.getString(3)
                val rangi = cursor.getString(4)
                val fasli = cursor.getString(5)
                val imageByteArray = cursor.getBlob(6)
                val image = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                val mahsulotlar = Mahsulotlar(id, nomi, narxi, olchami, rangi, fasli, rasm = image)
                mahsulotlarList.add(mahsulotlar)
            } while (cursor.moveToNext())
        }

        return mahsulotlarList
    }

    override fun updateMahsulot(mahsulotlar: Mahsulotlar) {
        val contentValues = ContentValues()
        contentValues.put(MAHSULOT_FASLI, mahsulotlar.fasli)
        contentValues.put(MAHSULOT_NARXI, mahsulotlar.narxi)
        contentValues.put(MAHSULOT_NOMI, mahsulotlar.nomi)
        contentValues.put(MAHSULOT_RANGI, mahsulotlar.rangi)
        contentValues.put(MAHSULOT_OLCHAMI, mahsulotlar.olchami)
        val byteArrayOutputStream = ByteArrayOutputStream()
        mahsulotlar.rasm!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val rasm = byteArrayOutputStream.toByteArray()
        contentValues.put(IMG, rasm)
        this.writableDatabase.update(TABLE_MAHSULOT, contentValues, "$ID = ?", arrayOf(mahsulotlar.id.toString()))
    }

    override fun deleteMahsulot(mahsulotlar: Mahsulotlar) {
        this.writableDatabase.delete(TABLE_MAHSULOT, "$ID = ?", arrayOf(mahsulotlar.id.toString()))
    }

    override fun addBuyurtma(buyurtmalar: Buyurtmalar) {
        val contentValues = ContentValues()
        contentValues.put(BUYURTMA_MIQDORI, buyurtmalar.olchami)
        contentValues.put(BUYURTMA_NARXI, buyurtmalar.narxi)
        contentValues.put(BUYURTMA_NOMI, buyurtmalar.nomi)
        contentValues.put(BUYURTMA_RANGI, buyurtmalar.rangi)
        val byteArrayOutputStream = ByteArrayOutputStream()
        buyurtmalar.rasm!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val rasm = byteArrayOutputStream.toByteArray()
        contentValues.put(IMG, rasm)
        this.writableDatabase.insert(TABLE_BUYURTMA, null, contentValues)
    }

    override fun getBuyurtma(): MutableList<Buyurtmalar> {
        val buyurtmalarList = mutableListOf<Buyurtmalar>()

        val cursor = this.writableDatabase.rawQuery("SELECT * FROM $TABLE_BUYURTMA", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val nomi = cursor.getString(1)
                val narxi = cursor.getString(2)
                val miqdor = cursor.getString(3)
                val rangi = cursor.getString(4)
                val imageByteArray = cursor.getBlob(5)
                val image = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                val buyurtmalar = Buyurtmalar(id, narxi, nomi, miqdor, rangi, rasm = image)
                buyurtmalarList.add(buyurtmalar)
            } while (cursor.moveToNext())
        }

        return buyurtmalarList
    }

    override fun updateBuyurtma(buyurtmalar: Buyurtmalar) {
        val contentValues = ContentValues()
        contentValues.put(BUYURTMA_MIQDORI, buyurtmalar.olchami)
        contentValues.put(BUYURTMA_NARXI, buyurtmalar.narxi)
        contentValues.put(BUYURTMA_NOMI, buyurtmalar.nomi)
        contentValues.put(BUYURTMA_RANGI, buyurtmalar.rangi)
        val byteArrayOutputStream = ByteArrayOutputStream()
        buyurtmalar.rasm!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val rasm = byteArrayOutputStream.toByteArray()
        contentValues.put(IMG, rasm)
        this.writableDatabase.update(TABLE_BUYURTMA, contentValues, "$ID = ?", arrayOf(buyurtmalar.id.toString()))
    }

    override fun deleteBuyurtma(buyurtmalar: Buyurtmalar) {
        this.writableDatabase.delete(TABLE_BUYURTMA, "$ID = ?", arrayOf(buyurtmalar.id.toString()))
    }


}