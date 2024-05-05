package com.strezh.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.strezh.shoppinglist.data.ShopListRepositoryImpl
import com.strezh.shoppinglist.domain.EditShopItemUseCase
import com.strezh.shoppinglist.domain.GetShopListUseCase
import com.strezh.shoppinglist.domain.RemoveShopItemUseCase
import com.strezh.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(item: ShopItem) {
        removeShopItemUseCase.removeShopItem(item)
    }

    fun changeEnabledState(item: ShopItem) {
        editShopItemUseCase.editShopItem(item.copy(isEnabled = !item.isEnabled))
    }
}