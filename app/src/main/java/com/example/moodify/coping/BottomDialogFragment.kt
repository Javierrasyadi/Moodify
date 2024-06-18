package com.example.moodify.coping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.moodify.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Display the first fragment
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.bottom_sheet_fragment_container, CopingToolsFragment1())
                .commit()
        }
    }

    fun navigateToFragment(fragment: Fragment, direction: String) {
        val transaction = childFragmentManager.beginTransaction()

        when (direction) {
            "left" -> transaction.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            "right" -> transaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }

        transaction.replace(R.id.bottom_sheet_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}