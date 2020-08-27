package com.tenten.yugibrick.domain.interactor

import com.tenten.yugibrick.domain.base.SingleUseCase
import com.tenten.yugibrick.domain.common.executor.PostExecutionThread
import com.tenten.yugibrick.domain.model.Combo
import com.tenten.yugibrick.domain.model.ComboCalculator
import com.tenten.yugibrick.domain.service.IDService
import com.tenten.yugibrick.domain.service.ProbabilityService
import io.reactivex.Single

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class CreateCombo(
    postExecutionThread: PostExecutionThread,
    private val idService: IDService,
    private val probabilityService: ProbabilityService
) : SingleUseCase<Combo, ComboCalculator>(postExecutionThread) {

    override fun buildObservable(param: ComboCalculator): Single<Combo> {
        return Single.just(
            Combo(
                id = idService.generateID(),
                cards = param.cards,
                probability = probabilityService.comboCalculator(param)
            )
        )
    }
}
