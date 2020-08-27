package com.tenten.yugibrick.view.drawer

import android.os.Bundle
import androidx.core.view.GravityCompat
import com.tenten.ui.TToolbar
import com.tenten.yugibrick.R
import com.tenten.yugibrick.view.base.BaseActivity
import com.tenten.yugibrick.view.base.BaseViewModel
import com.tenten.yugibrick.view.common.util.FragmentUtil
import com.tenten.yugibrick.view.calculator.CalculatorFragment
import kotlinx.android.synthetic.main.activity_drawer.drawer_layout
import kotlinx.android.synthetic.main.activity_drawer.nav_view
import kotlinx.android.synthetic.main.activity_drawer.ttoolbar

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class DrawerActivity(override val viewRes: Int = R.layout.activity_drawer) :
    BaseActivity() {

    override fun getToolbar(): TToolbar = ttoolbar
    override fun baseViewModel(): BaseViewModel? = null

    override fun initViews(savedInstanceState: Bundle?) {
        ttoolbar.setIconLeftClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        nav_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_calc -> FragmentUtil.attachFragment(
                    supportFragmentManager,
                    CalculatorFragment.newInstance(),
                    R.id.frame_content
                )

            }
            true
        }

        FragmentUtil.attachFragment(
            supportFragmentManager,
            CalculatorFragment.newInstance(),
            R.id.frame_content
        )
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
}
