package com.example.nazorat.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nazorat.fragments.ViewPager
import com.example.nazorat.models.Mahsulotlar

class ViewPagerAdapter(fm: Fragment, var list: MutableList<Mahsulotlar>) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 4
    }

    fun filter(list: MutableList<Mahsulotlar>) {
        this.list = list
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ViewPager.newInstance("qish", arrayListOf())
            }
            1 -> {
                ViewPager.newInstance("bahor", arrayListOf())
            }
            2 -> {
                ViewPager.newInstance("yoz", arrayListOf())
            }
            3 -> {
                ViewPager.newInstance("kuz", arrayListOf())
            }
            else -> {Fragment()}
        }
    }
}