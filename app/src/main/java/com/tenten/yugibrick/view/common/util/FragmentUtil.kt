package com.tenten.yugibrick.view.common.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


object FragmentUtil {

    fun attachFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        viewRes: Int,
        enter: Int = 0,
        exit: Int = 0
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
            .replace(viewRes, fragment, fragment::class.java.simpleName)

        if (enter != 0 && exit != 0) {
            fragmentTransaction.setCustomAnimations(enter, exit)
        }

        fragmentTransaction.commit()
    }

    @Suppress("LongParameterList")
    fun attachSharedElementFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        viewRes: Int,
        enter: Int,
        exit: Int,
        views: List<Pair<View, String>>
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
            .setCustomAnimations(enter, exit)
            .replace(viewRes, fragment, fragment::class.java.simpleName)

        for (view in views) {
            fragmentTransaction.addSharedElement(view.first, view.second)
        }

        fragmentTransaction.commit()
    }

    fun attachFragment(fragmentManager: FragmentManager, fragment: Fragment, viewRes: Int) {
        FragmentUtil.attachFragment(fragmentManager, fragment, viewRes, 0, 0)
    }

    /**
     * handles popping of fragments once back button is pressed on activity.
     */
    fun onActivityBackPressed(activity: AppCompatActivity, onBackPressed: () -> Unit) {
        val count = activity.supportFragmentManager.backStackEntryCount

        if (count == 1) {
            onBackPressed()
        } else {
            activity.supportFragmentManager.popBackStack()
        }
    }
}
