package com.skimani.inventory.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.skimani.inventory.R
import com.skimani.inventory.ui.main.* // ktlint-disable no-wildcard-imports
import timber.log.Timber

private val TAB_TITLES = arrayOf(
    R.string.all,
    R.string.cereals_seeds,
    R.string.equipments,
    R.string.minerals,
    R.string.vegetables
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Timber.d("Position $position")
        return when (position) {
            0 -> AllProductsFragment.newInstance(position)
            1 -> CerealsFragment.newInstance(position)
            2 -> EquipmentsFragment.newInstance(position)
            3 -> MineralsFragment.newInstance(position)
            4 -> VegetablesFragment.newInstance(position)
            else -> AllProductsFragment.newInstance(position)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}
