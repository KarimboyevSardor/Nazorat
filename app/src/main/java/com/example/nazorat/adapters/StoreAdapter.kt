package com.example.nazorat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nazorat.databinding.StoreItemBinding
import com.example.nazorat.models.Buyurtmalar
import com.example.nazorat.models.Mahsulotlar

class StoreAdapter(var list: MutableList<Buyurtmalar>, val listener: OnItemClick) : Adapter<StoreAdapter.MyVh>() {
        fun filter(list: MutableList<Buyurtmalar>) {
        this.list = list
        notifyDataSetChanged()
    }
    class MyVh(val binding: StoreItemBinding) : ViewHolder(binding.root)
    interface OnItemClick {
        fun OnClick(buyurtmalarId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        return MyVh(StoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyVh, position: Int) {
        holder.binding.price.text = list[position].narxi
        holder.binding.name.text = list[position].nomi
        holder.binding.image.setImageBitmap(list[position].rasm)

        holder.itemView.setOnClickListener {
            listener.OnClick(list[position].id)
        }
    }


}