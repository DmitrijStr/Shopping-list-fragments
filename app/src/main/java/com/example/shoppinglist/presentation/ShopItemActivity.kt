package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)


        viewModel.errorInputName.observe(this) {

        }

    }

}