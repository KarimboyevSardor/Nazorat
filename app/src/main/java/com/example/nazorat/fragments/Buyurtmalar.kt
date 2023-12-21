package com.example.nazorat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nazorat.R
import com.example.nazorat.adapters.StoreAdapter
import com.example.nazorat.databinding.FragmentBuyurtmalarBinding
import com.example.nazorat.models.Mahsulotlar
import com.example.nazorat.myobjects.MyObjects.buyurtmalarList
import com.example.nazorat.myobjects.MyObjects.getViewModel
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Buyurtmalar : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var binding: FragmentBuyurtmalarBinding? = null
    lateinit var storeAdapter: StoreAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBuyurtmalarBinding.inflate(inflater, container, false)

        binding!!.apply {
            val viewModel = getViewModel(requireActivity())
            storeAdapter = StoreAdapter(buyurtmalarList, object : StoreAdapter.OnItemClick{
                override fun OnClick(buyurtmalarId: Int) {
                    val fm = Info()
                    val bundle = Bundle()
                    bundle.putInt("mah", buyurtmalarId)
                    bundle.putString("is", "b")
                    fm.arguments = bundle
                    findNavController().navigate(R.id.action_buyurtmalar_to_info2, bundle)
                }
            })

            storeRec.adapter = storeAdapter
            viewModel.getBuyurtma()!!.observe(requireActivity()) {
                storeAdapter.filter(it)
            }
        }

        return binding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Buyurtmalar().apply {
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