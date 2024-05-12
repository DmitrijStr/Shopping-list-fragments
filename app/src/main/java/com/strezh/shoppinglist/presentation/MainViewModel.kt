package com.strezh.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strezh.shoppinglist.data.ShopListRepositoryImpl
import com.strezh.shoppinglist.domain.EditShopItemUseCase
import com.strezh.shoppinglist.domain.GetShopListUseCase
import com.strezh.shoppinglist.domain.RemoveShopItemUseCase
import com.strezh.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(item: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(item)
        }
    }

    fun changeEnabledState(item: ShopItem) {
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(item.copy(isEnabled = !item.isEnabled))
        }
    }
}