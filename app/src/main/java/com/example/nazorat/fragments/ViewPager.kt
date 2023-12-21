package com.example.nazorat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.nazorat.R
import com.example.nazorat.adapters.AsosiyAdapter
import com.example.nazorat.databinding.FragmentViewPagerBinding
import com.example.nazorat.models.Buyurtmalar
import com.example.nazorat.models.Mahsulotlar
import com.example.nazorat.mydb.MyDb
import com.example.nazorat.myobjects.MyObjects.buyurtma
import com.example.nazorat.myobjects.MyObjects.buyurtmalarList
import com.example.nazorat.myobjects.MyObjects.getViewModel
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewPager : Fragment() {
    private var param1: String? = null
    private var param2: MutableList<Mahsulotlar>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getParcelableArrayList<Mahsulotlar>(ARG_PARAM2)
        }
    }


    lateinit var asosiyAdapter: AsosiyAdapter
    lateinit var myDb: MyDb
    private var binding: FragmentViewPagerBinding? = null
    private var count = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        binding!!.apply {
            myDb = MyDb(requireContext())
            val viewModel = getViewModel(requireActivity())
            asosiyAdapter = AsosiyAdapter(mahsulotlarList.filter { it.fasli == param1 } as ArrayList, object : AsosiyAdapter.OnItemPressed{
                override fun onClickItem(mahsulotlarId: Int) {
                    val fm = Info()
                    val bundle = Bundle()
                    bundle.putInt("mah", mahsulotlarId)
                    bundle.putString("is", "m")
                    fm.arguments = bundle
                    findNavController().navigate(R.id.action_asosiy_to_info2, bundle)
                }

                override fun addButton(count1: TextView, mahsulotlarId: Int) {
                    if (count1.text.toString().toInt() == 0) {
                        count = 0
                        count = count + 1
                        count1.text = count.toString()
                        val mah = mahsulotlarList.filter { it.id == mahsulotlarId }[0]
                        buyurtma.add(Buyurtmalar(narxi = mah.narxi!!, nomi = mah.nomi!!, olchami = mah.olchami!!, rasm = mah.rasm, rangi = mah.rangi))
                        viewModel.buyurtmaMutableLiveData!!.value = buyurtma
                    } else {
                        count = count1.text.toString().toInt()
                        count = count + 1
                        count1.text = count.toString()
                    }
                }

                override fun removeButton(count1: TextView, mahsulotlarId: Int) {
                    if (count1.text.toString().toInt() > 0) {
                        count =  count1.text.toString().toInt()
                        count--
                        if (count > 0) {
                            count1.text = count.toString()
                        } else if (count == 0) {
                            val mah = mahsulotlarList.filter { it.id == mahsulotlarId }[0]
                            buyurtma.add(Buyurtmalar(narxi = mah.narxi!!, nomi = mah.nomi!!, olchami = mah.olchami!!, rasm = mah.rasm, rangi = mah.rangi))
                            buyurtma.removeIf { it == Buyurtmalar(narxi = mah.narxi!!, nomi = mah.nomi!!, olchami = mah.olchami!!, rasm = mah.rasm, rangi = mah.rangi)}
                            viewModel.buyurtmaMutableLiveData!!.value = buyurtma
                            count = 0
                            count1.text = count.toString()
                        }
                    }
                }
            })

            viewModel.getMahsulot()!!.observe(requireActivity()) {
                asosiyAdapter.filter(it.filter { it.fasli == param1 } as ArrayList)
            }
            recAsosiy.adapter = asosiyAdapter


        }

        return binding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: ArrayList<Mahsulotlar>) =
            ViewPager().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putParcelableArrayList(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}