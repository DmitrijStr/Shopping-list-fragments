package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopItems = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_enabled, parent, false
        )
        return ShopListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shopItems.size
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val item = shopItems[position]

        holder.name.text = item.name
        holder.count.text = item.count.toString()
        holder.view.setOnLongClickListener {
            true
        }
    }

    class ShopListViewHolder(val view: View) : ViewHolder(view) {
        val name: TextView = view.findViewById<TextView>(R.id.tv_name)
        val count = view.findViewById<TextView>(R.id.tv_count)
    }
}