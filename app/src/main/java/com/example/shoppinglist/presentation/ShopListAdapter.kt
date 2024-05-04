package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == STATE_ENABLED) {
                R.layout.item_shop_enabled
            } else {
                R.layout.item_shop_disabled
            }, parent, false
        )
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val item = getItem(position)

        holder.name.text = item.name
        holder.count.text = item.count.toString()
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(item)
        }
        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(item)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isEnabled) STATE_ENABLED else STATE_DISABLED
    }

    companion object {
        const val STATE_DISABLED = 0
        const val STATE_ENABLED = 1

        const val MAX_POOL_SIZE = 15
    }
}