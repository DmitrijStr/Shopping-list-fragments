package com.strezh.shoppinglist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.strezh.shoppinglist.data.ShopListRepositoryImpl
import com.strezh.shoppinglist.domain.AddShopItemUseCase
import com.strezh.shoppinglist.domain.EditShopItemUseCase
import com.strezh.shoppinglist.domain.GetShopItemUseCase
import com.strezh.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>(false)
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>(false)
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    init {
        Log.d("ShopItemViewModel", "$this")
    }
    fun getShopItem(itemId: Int) {
        viewModelScope.launch {
            _shopItem.value = getShopItemUseCase.getShopItem(itemId)
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(ShopItem(name, count, true))
            }
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            _shopItem.value?.let {
                val newItem = it.copy(name = name,count = count)
                viewModelScope.launch {
                    editShopItemUseCase.editShopItem(newItem)
                }
                finishWork()
            }
        }
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            _errorInputName.value = true
            isValid = false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            isValid = false
        }

        return isValid
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
    }
}