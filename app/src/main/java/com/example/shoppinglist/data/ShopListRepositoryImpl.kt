package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>(object : Comparator<ShopItem> {
        override fun compare(o1: ShopItem, o2: ShopItem): Int {
            return o1.id - o2.id
        }
    })

    private var currId = 0

    init {
        repeat(100) {
            addShopItem(ShopItem("Item $it", 1, true))
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = currId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id)
        shopList.remove(oldItem)
        addShopItem(item)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id }
            ?: throw RuntimeException("Element with id: $id not found")
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}