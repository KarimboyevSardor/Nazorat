package com.example.nazorat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nazorat.databinding.MahsulotItemBinding
import com.example.nazorat.models.Mahsulotlar

class AsosiyAdapter(private var list: MutableList<Mahsulotlar>, private val listener: OnItemPressed) : Adapter<AsosiyAdapter.MyVh>() {
    private var count = 0
    private var pos1 = -1
    class MyVh(val binding: MahsulotItemBinding) : ViewHolder(binding.root)

    fun filter(list: MutableList<Mahsulotlar>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface OnItemPressed {
        fun onClickItem(mahsulotlarId: Int)
        fun addButton(count: TextView, mahsulotlarId: Int)
        fun removeButton(count: TextView, mahsulotlarId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVh {
        return MyVh(MahsulotItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyVh, position: Int) {
        holder.binding.price.text = list[position].narxi
        holder.binding.name.text = list[position].nomi
        holder.binding.image.setImageBitmap(list[position].rasm)

        holder.itemView.setOnClickListener {
            listener.onClickItem(list[position].id)
        }

        holder.binding.add.setOnClickListener {
            listener.addButton(holder.binding.count, list[position].id)
        }
        holder.binding.remove.setOnClickListener {
            listener.removeButton(holder.binding.count, list[position].id)
        }
    }


}