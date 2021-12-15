package com.skimani.inventory.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skimani.inventory.adapter.InventoryAdapter
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.databinding.FragmentMainBinding
import com.skimani.inventory.ui.viewmodel.ProductsViewmodel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class EquipmentsFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private lateinit var inventoryAdapter: InventoryAdapter
    private val vegList = ArrayList<Products>()
    private val productsViewmodel: ProductsViewmodel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        initAdapter()
    }

    private fun initAdapter() {
        inventoryAdapter = InventoryAdapter()
        binding.productsRV.adapter = inventoryAdapter
        inventoryAdapter.submitList(vegList)
    }

    private fun setupObservers() {
        productsViewmodel.filteredProducts("equipments").observe(viewLifecycleOwner, {
            Timber.d("Item count ${it.size}")
            if (it != null) {
                vegList.clear()
                vegList.addAll(it.toMutableList())
                inventoryAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        initViews()
        setupObservers()
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
        fun newInstance(sectionNumber: Int): EquipmentsFragment {
            return EquipmentsFragment().apply {
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
