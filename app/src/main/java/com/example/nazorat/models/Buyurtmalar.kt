package com.example.nazorat.models

import android.graphics.Bitmap

data class Buyurtmalar(
    var id: Int = 0,
    var narxi: String = "",
    var nomi: String = "",
    var olchami: String = "",
    var rangi: String? = "",
    var rasm: Bitmap? = null
)
