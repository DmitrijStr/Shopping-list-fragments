package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var currId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = currId
        }
        shopList.add(shopItem)
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id)
        shopList.remove(oldItem)
        addShopItem(item)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException("Element with id: $id not found")
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}