package com.example.nazorat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nazorat.R
import com.example.nazorat.databinding.FragmentInfoBinding
import com.example.nazorat.models.Mahsulotlar
import com.example.nazorat.myobjects.MyObjects.buyurtma
import com.example.nazorat.myobjects.MyObjects.buyurtmalarList
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Info : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var binding: FragmentInfoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)

        binding!!.apply {
            val mah = arguments?.getInt("mah")!!
            val is1 = arguments?.getString("is")!!
            if (is1 == "m") {
                val mahsulotlar = mahsulotlarList.filter { it.id == mah }[0]
                img.setImageBitmap(mahsulotlar.rasm)
                narxi.text = mahsulotlar.narxi
                nomi.text = mahsulotlar.nomi
                olchami.text = mahsulotlar.olchami
                rangi.text = mahsulotlar.rangi
            } else if (is1 == "b") {
                val mahsulotlar = buyurtmalarList.filter { it.id == mah }[0]
                img.setImageBitmap(mahsulotlar.rasm)
                narxi.text = mahsulotlar.narxi
                nomi.text = mahsulotlar.nomi
                olchami.text = mahsulotlar.olchami
                rangi.text = mahsulotlar.rangi
            }
        }

        return binding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Info().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}