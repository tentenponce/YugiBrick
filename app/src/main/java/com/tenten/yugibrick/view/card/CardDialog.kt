package com.tenten.yugibrick.view.card

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import com.tenten.yugibrick.R
import com.tenten.yugibrick.view.base.BaseDialog
import com.tenten.yugibrick.view.combo.ComboViewModel
import kotlinx.android.synthetic.main.dialog_card.view.tbtn_add
import kotlinx.android.synthetic.main.dialog_card.view.tet_count
import kotlinx.android.synthetic.main.dialog_card.view.tet_name
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class CardDialog : BaseDialog() {

    private val viewModel by sharedViewModel<ComboViewModel>()

    override val viewRes: Int = R.layout.dialog_card

    private lateinit var layoutView: View

    override fun initViews(view: View, savedInstanceState: Bundle?, dialog: AppCompatDialog) {
        layoutView = view

        view.tbtn_add.setOnClickListener {
            val name = view.tet_name.getEditText().text.toString()
            val copies = view.tet_count.getEditText().text.toString()

            viewModel.addCard(name, copies)
        }
    }

    override fun initViewModels() {
    }
}
