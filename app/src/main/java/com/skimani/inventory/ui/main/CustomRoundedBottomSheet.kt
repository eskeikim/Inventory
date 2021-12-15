package com.skimani.inventory.ui.main

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.skimani.inventory.R

/**
 * Custom Base Bottom sheet
 */
open class CustomRoundedBottomSheet : BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = STATE_EXPANDED
        return dialog
    }
}
