package com.example.nazorat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nazorat.R
import com.example.nazorat.adapters.EditsAdapter
import com.example.nazorat.databinding.FragmentMahsulotlarniOzgartirishBinding
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MahsulotlarniOzgartirish : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var editsAdapter: EditsAdapter
    private var binding: FragmentMahsulotlarniOzgartirishBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMahsulotlarniOzgartirishBinding.inflate(inflater, container, false)

        binding!!.apply {
            editsAdapter = EditsAdapter(mahsulotlarList, object : EditsAdapter.OnItemClick{
                override fun OnClick(mahsulotlarId: Int) {
                    val fm = Edits()
                    val bundle = Bundle()
                    bundle.putInt("mah", mahsulotlarId)
                    fm.arguments = bundle
                    findNavController().navigate(R.id.action_mahsulotlarniOzgartirish_to_edits, bundle)
                }
            })

            editRec.adapter = editsAdapter
        }

        return binding!!.root
    }



    companion object {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}