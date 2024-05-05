package com.strezh.shoppinglist.domain

class RemoveShopItemUseCase(private val repository: ShopListRepository) {

    fun removeShopItem(shopItem: ShopItem) {
        repository.removeShopItem(shopItem)
    }
}