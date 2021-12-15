package com.skimani.inventory.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.skimani.inventory.databinding.AddProductBinding
import com.skimani.inventory.ui.viewmodel.ProductsViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductDialog : CustomRoundedBottomSheet() {
    val productsViewmodel: ProductsViewmodel by viewModels()
    lateinit var binding: AddProductBinding
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
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun newInstance(): AddProductDialog =
            AddProductDialog().apply {
                arguments = Bundle().apply {}
            }
    }
}
