package com.strezh.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.strezh.shoppinglist.domain.ShopItem
import com.strezh.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)
    }

}