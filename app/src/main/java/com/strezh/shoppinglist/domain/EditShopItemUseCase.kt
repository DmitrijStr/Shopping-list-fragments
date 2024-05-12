package com.strezh.shoppinglist.domain

class EditShopItemUseCase(private val repository: ShopListRepository) {

    suspend fun editShopItem(item: ShopItem) {
        repository.editShopItem(item)
    }
}