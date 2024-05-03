package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llListView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        llListView = findViewById(R.id.ll_layout)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            showList(it)
        }
    }

    fun showList(list: List<ShopItem>) {
        llListView.removeAllViews()

        for (item in list) {
            val layoutId = if (item.isEnabled) R.layout.item_shop_enabled else R.layout.item_shop_disabled

            val view = LayoutInflater.from(this).inflate(layoutId, llListView, false)
            val name = view.findViewById<TextView>(R.id.tv_name)
            val cnt = view.findViewById<TextView>(R.id.tv_count)
            name.text = item.name
            cnt.text = item.count.toString()

            view.setOnLongClickListener {
                viewModel.changeEnabledState(item)
                true
            }

            llListView.addView(view)
        }
    }
}