package com.skimani.inventory

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.skimani.inventory.adapter.SectionsPagerAdapter
import com.skimani.inventory.databinding.ActivityMainBinding
import com.skimani.inventory.ui.main.AddProductDialog
import com.skimani.inventory.ui.viewmodel.ProductsViewmodel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productsViewmodel: ProductsViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab
        initSearchView()

        fab.setOnClickListener { view ->
            AddProductDialog.newInstance().show(supportFragmentManager, "dialog")
        }
        binding.createNew.setOnClickListener { view ->
            AddProductDialog.newInstance().show(supportFragmentManager, "dialog")
        }
    }

    private fun initSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Timber.d("onQueryTextChangequery: $s")
                val search = s?.toString()?.toLowerCase(Locale.US).toString()
                if (search!!.isNotEmpty()) {
//                    productsViewmodel.searchProducts(search)
                } else {
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}
