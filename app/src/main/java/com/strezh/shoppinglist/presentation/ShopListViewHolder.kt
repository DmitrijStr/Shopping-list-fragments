package com.strezh.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.strezh.shoppinglist.R


class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById<TextView>(R.id.tv_name)
    val count = view.findViewById<TextView>(R.id.tv_count)
}
