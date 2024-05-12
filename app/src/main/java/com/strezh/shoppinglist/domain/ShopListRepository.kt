package com.strezh.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(id: Int): ShopItem

    suspend fun removeShopItem(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>
}