package com.strezh.shoppinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.strezh.shoppinglist.R

class ShopItemFragment : Fragment() {
    private lateinit var viewModel: ShopItemViewModel
    private lateinit var tilEditName: TextInputLayout
    private lateinit var tilEditCount: TextInputLayout
    private lateinit var editName: EditText
    private lateinit var editCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setMode()
        observeViewModel()
        addTextChangeListeners()
    }

    private fun addTextChangeListeners() {
        editName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}
        })

        editCount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun parseParams() {
        val args = requireArguments()

        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)

        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }


    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) { isError ->
            val msg = if (isError) getString(R.string.error_input_name) else null
            tilEditName.error = msg
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) { isError ->
            val msg = if (isError) getString(R.string.error_input_count) else null
            tilEditCount.error = msg
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(editName.text?.toString(), editCount.text?.toString())
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)

        viewModel.shopItem.observe(viewLifecycleOwner) {
            editName.setText(it.name)
            editCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(editName.text?.toString(), editCount.text?.toString())
        }
    }

    private fun initViews(view: View) {
        tilEditName = view.findViewById(R.id.til_name)
        tilEditCount = view.findViewById(R.id.til_count)
        editName = view.findViewById(R.id.et_name)
        editCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
        private const val UNDEFINED_ID = -1

        fun newInstanceAddItem(): ShopItemFragment {
            val args = Bundle().apply {
                putString(SCREEN_MODE, MODE_ADD)
            }
            return ShopItemFragment().apply { arguments = args }
        }

        fun newInstanceEditItem(itemId: Int): ShopItemFragment {
            val args = Bundle().apply {
                putString(SCREEN_MODE, MODE_EDIT)
                putInt(SHOP_ITEM_ID, itemId)
            }

            return ShopItemFragment().apply { arguments = args }
        }
    }
}