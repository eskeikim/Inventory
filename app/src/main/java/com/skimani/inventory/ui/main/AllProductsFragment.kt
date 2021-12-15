package com.skimani.inventory.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.databinding.FragmentMainBinding
import com.skimani.inventory.ui.viewmodel.PageViewModel
import com.skimani.inventory.ui.viewmodel.ProductsViewmodel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class AllProductsFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null
    private val productsViewmodel: ProductsViewmodel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root
        initViews()

        setupObservers()
        return root
    }

    private fun initViews() {
        saveProduct()
    }

    private fun saveProduct() {
        val product= Products(
            "Rice",
            30002,
            "Cereals",
            "None",
            "Mwea Rice",
            "Mwea Distributors",
            "12000",
            "18000",
            "3000",
            "7000",
            ""
        )
        productsViewmodel.addProducts(product)
    }

    private fun setupObservers() {
        pageViewModel.text.observe(
            viewLifecycleOwner,
            {
                binding.sectionLabel.text = it
            }
        )

        productsViewmodel.allProducts.observe(viewLifecycleOwner, {
            Timber.d("Item count ${it.size}")
        })
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): AllProductsFragment {
            return AllProductsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
