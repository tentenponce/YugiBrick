package com.tenten.yugibrick.view.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.tenten.yugibrick.R
import com.tenten.yugibrick.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.lin_developer
import kotlinx.android.synthetic.main.fragment_about.lin_github


/**
 *
 * Created by Exequiel Egbert V. Ponce on 9/1/2020.
 */
class AboutFragment : BaseFragment() {

    companion object {
        private const val FB_URL = "https://www.facebook.com/tenponce"
        private const val GITHUB_URL = "https://github.com/tentenponce/YugiBrick"

        fun newInstance(): AboutFragment {
            val fragment = AboutFragment()

            val bundle = Bundle()
            fragment.arguments = bundle

            return fragment
        }
    }

    override val viewRes: Int = R.layout.fragment_about

    override fun initViews(savedInstanceState: Bundle?) {
        /* init bindings */
        lin_developer.setOnClickListener { redirect(FB_URL) }
        lin_github.setOnClickListener { redirect(GITHUB_URL) }
    }

    override fun initViewModels() {
    }

    private fun redirect(url: String) {
        val httpIntent = Intent(Intent.ACTION_VIEW)
        httpIntent.data = Uri.parse(url)

        startActivity(httpIntent)
    }
}
