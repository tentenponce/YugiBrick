package com.tenten.yugibrick.di

import com.tenten.yugibrick.domain.common.executor.PostExecutionThread
import com.tenten.yugibrick.domain.interactor.CalculateCombos
import com.tenten.yugibrick.domain.interactor.CalculateDeckCombo
import com.tenten.yugibrick.domain.interactor.CreateCombo
import com.tenten.yugibrick.domain.service.IDService
import com.tenten.yugibrick.domain.service.ProbabilityService
import com.tenten.yugibrick.view.common.executor.PostExecutionThreadImpl
import org.koin.dsl.module.module

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
val domainModule = module {
    /* executors */
    single { PostExecutionThreadImpl() as PostExecutionThread }

    /* interactors */
    single { CreateCombo(get(), get(), get()) }
    single { CalculateDeckCombo(get(), get()) }
    single { CalculateCombos(get(), get()) }

    /* services */
    single { IDService() }
    single { ProbabilityService() }
}
