package com.tenten.yugibrick.di

import com.tenten.yugibrick.view.combo.ComboViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
val appModule = module {
    viewModel { ComboViewModel() }
}
