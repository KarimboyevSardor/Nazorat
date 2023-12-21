package com.example.nazorat.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class Mahsulotlar(
    var id: Int = 0,
    var nomi: String? = "",
    var narxi: String? = "",
    var olchami: String? = "",
    var rangi: String? = "",
    var fasli: String? = "",
    var rasm: Bitmap? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nomi)
        parcel.writeString(narxi)
        parcel.writeString(olchami)
        parcel.writeString(rangi)
        parcel.writeString(fasli)
        parcel.writeParcelable(rasm, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mahsulotlar> {
        override fun createFromParcel(parcel: Parcel): Mahsulotlar {
            return Mahsulotlar(parcel)
        }

        override fun newArray(size: Int): Array<Mahsulotlar?> {
            return arrayOfNulls(size)
        }
    }
}