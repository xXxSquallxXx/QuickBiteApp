package com.example.quickbiteapp.ui.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.quickbiteapp.R

object NavigationManager {
    private var fragmentManager: FragmentManager? = null

    fun setFragmentManager(manager: FragmentManager) {
        fragmentManager = manager
    }

    fun replaceFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    fun replaceFragmentForRestaurant(fragment: Fragment) {
        fragmentManager?.let { fm ->
            if (fm.isDestroyed) return
            val currentFragment = fm.fragments.lastOrNull()
            fm.beginTransaction().apply {
                currentFragment?.let { remove(it) }
                replace(R.id.fragment_container, fragment)
                commit()
            }
        }
    }

    fun onBackStackChanged() {
        fragmentManager?.let { fm ->
            fm.fragments.forEach { fragment ->
                fm.beginTransaction()
                    .apply {
                        if (fragment == fm.fragments.lastOrNull()) show(fragment) else hide(fragment)
                    }
                    .commit()
            }
        }
    }
}