package com.skimani.inventory.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.skimani.inventory.R
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.databinding.AddProductBinding
import com.skimani.inventory.ui.viewmodel.ProductsViewmodel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddProductDialog : CustomRoundedBottomSheet() {
    val productsViewmodel: ProductsViewmodel by viewModels()
    lateinit var binding: AddProductBinding
    private var selectedItem=""
    val contactId: MutableList<Long> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddProductBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initViews()
        return binding.root
    }

    private fun initViews() {

        initSpinner()
        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.saveBtn.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val code = binding.etCode.text.toString().trim()
            val category = selectedItem
            val type = binding.etType.text.toString().trim()
            val manufacturer = binding.etManufacturer.text.toString().trim()
            val distributor = binding.etDistributor.text.toString().trim()
            val cost = binding.etCost.text.toString().trim()
            val retailPrice = binding.etRetailPrice.text.toString().trim()
            val agentPrice = binding.etAgentPrice.text.toString().trim()
            val wholesalePrice = binding.etWholesalePrice.text.toString().trim()
            val imagePath = "image"
            if (validateFields(
                    name,
                    code,
                    category,
                    type,
                    manufacturer,
                    distributor,
                    cost,
                    retailPrice,
                    agentPrice,
                    wholesalePrice
                )
            ) {
                binding.saveBtn.isEnabled = true
                binding.saveBtn.alpha = 0.3F
                saveProduct(
                    name,
                    code,
                    category,
                    type,
                    manufacturer,
                    distributor,
                    cost,
                    retailPrice,
                    agentPrice,
                    wholesalePrice,
                    imagePath
                )
                dismiss()
            }
        }
    }

    private fun initSpinner() {
        val categoriesList = resources.getStringArray(R.array.category)
        val categoryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            categoriesList
        )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = categoryAdapter
        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    Timber.d("SELECTED ${p0?.selectedItem}")
                    selectedItem=p0?.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    private fun saveProduct(
        name: String,
        code: String,
        category: String,
        type: String,
        manufacture: String,
        distributor: String,
        cost: String,
        retailPrice: String,
        agentPrice: String,
        wholesalePrice: String,
        imagePath: String
    ) {
        val product = Products(
            name,
            code,
            category,
            type,
            manufacture,
            distributor,
            cost,
            retailPrice,
            agentPrice,
            wholesalePrice,
            imagePath
        )
        productsViewmodel.addProducts(product)
    }

    private fun validateFields(
        name: String,
        code: String,
        category: String,
        type: String,
        manufacturer: String,
        distributor: String,
        cost: String,
        retailPrice: String,
        agentPrice: String,
        wholesalePrice: String
    ): Boolean {
        return !(
            name.isEmpty() || code
                .isEmpty() || category.isEmpty() || type.isEmpty() || manufacturer.isEmpty() || distributor.isEmpty() || cost.isEmpty() || retailPrice.isEmpty() || agentPrice.isEmpty() || wholesalePrice.isEmpty()
            )
    }

    companion object {
        fun newInstance(): AddProductDialog =
            AddProductDialog().apply {
                arguments = Bundle().apply {}
            }
    }
}
