package com.example.nazorat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.nazorat.R
import com.example.nazorat.adapters.ViewPagerAdapter
import com.example.nazorat.databinding.FragmentAsosiyBinding
import com.example.nazorat.mydb.MyDb
import com.example.nazorat.myobjects.MyObjects.buyurtma
import com.example.nazorat.myobjects.MyObjects.buyurtmalarList
import com.example.nazorat.myobjects.MyObjects.getViewModel
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList
import com.google.android.material.tabs.TabLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Asosiy : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var binding: FragmentAsosiyBinding? = null
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var myDb: MyDb
    lateinit var tabName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAsosiyBinding.inflate(inflater, container, false)

        binding!!.apply {
            val viewModel = getViewModel(requireActivity())
            val activity = activity as AppCompatActivity
            activity.setSupportActionBar(toolbar)
            myDb = MyDb(requireContext())
            myDb = MyDb(requireContext())
            setHasOptionsMenu(true)
            mahsulotlarList = myDb.getMahsulot()
            buyurtmalarList = myDb.getBuyurtma()
            viewModel.mahsulotlarMutableLiveData!!.value = mahsulotlarList
            viewModel.buyurtmalarMutableLiveData!!.value = buyurtmalarList
            viewPagerAdapter = ViewPagerAdapter(this@Asosiy, mahsulotlarList)
            viewpager2.adapter = viewPagerAdapter
            tablelayout.addTab(tablelayout.newTab().setText("qish"))
            tablelayout.addTab(tablelayout.newTab().setText("bahor"))
            tablelayout.addTab(tablelayout.newTab().setText("yoz"))
            tablelayout.addTab(tablelayout.newTab().setText("kuz"))
            tabName = "qish"
            tablelayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewpager2.currentItem = tab.position
                        tabName = tab.text.toString()
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
            viewModel.getMahsulot()!!.observe(requireActivity()) { it ->
                viewPagerAdapter.filter(it.filter { it.fasli == tabName } as ArrayList)
            }
            viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tablelayout.selectTab(tablelayout.getTabAt(position))
                }
            })

            MahsulotlarniYangilash.setOnClickListener {
                findNavController().navigate(R.id.action_asosiy_to_mahsulotlarniOzgartirish)
            }
            savat.setOnClickListener {
                for (i in 0 until buyurtma.size) {
                    myDb.addBuyurtma(buyurtma[i])
                }
                buyurtmalarList = myDb.getBuyurtma()
                viewModel.buyurtmalarMutableLiveData!!.value = buyurtmalarList
                buyurtma.clear()
            }

        }

        return binding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Asosiy().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
     }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.buyurtma -> {
                findNavController().navigate(R.id.action_asosiy_to_buyurtmalar)
            }
            R.id.add1 -> {
                findNavController().navigate(R.id.action_asosiy_to_addMahsulot)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}