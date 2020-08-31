package com.tenten.yugibrick.view.drawer

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.tenten.ui.TToolbar
import com.tenten.yugibrick.R
import com.tenten.yugibrick.view.about.AboutFragment
import com.tenten.yugibrick.view.base.BaseActivity
import com.tenten.yugibrick.view.base.BaseViewModel
import com.tenten.yugibrick.view.calculator.CalculatorFragment
import com.tenten.yugibrick.view.common.util.FragmentUtil
import kotlinx.android.synthetic.main.activity_drawer.drawer_layout
import kotlinx.android.synthetic.main.activity_drawer.nav_view
import kotlinx.android.synthetic.main.activity_drawer.ttoolbar

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class DrawerActivity(override val viewRes: Int = R.layout.activity_drawer) :
    BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun getToolbar(): TToolbar = ttoolbar
    override fun baseViewModel(): BaseViewModel? = null

    override fun initViews(savedInstanceState: Bundle?) {
        ttoolbar.setIconLeftClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        nav_view.setNavigationItemSelectedListener(this)

        FragmentUtil.attachFragment(
            supportFragmentManager,
            CalculatorFragment.newInstance(),
            R.id.frame_content
        )

        nav_view.setCheckedItem(R.id.menu_calc)
    }

    override fun initViewModels() {
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_calc -> {
                FragmentUtil.attachFragment(
                    supportFragmentManager,
                    CalculatorFragment.newInstance(),
                    R.id.frame_content
                )
            }
            R.id.menu_about -> {
                FragmentUtil.attachFragment(
                    supportFragmentManager,
                    AboutFragment.newInstance(),
                    R.id.frame_content
                )
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
