package com.tenten.yugibrick.domain.interactor

import com.tenten.yugibrick.domain.base.SingleUseCase
import com.tenten.yugibrick.domain.common.executor.PostExecutionThread
import com.tenten.yugibrick.domain.model.DeckCalculator
import com.tenten.yugibrick.domain.service.ProbabilityService
import io.reactivex.Single

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class CalculateDeckCombo(
    postExecutionThread: PostExecutionThread,
    private val probabilityService: ProbabilityService
) : SingleUseCase<Float, DeckCalculator>(postExecutionThread) {

    override fun buildObservable(param: DeckCalculator): Single<Float> {
        return Single.just(probabilityService.calculate(param))
    }
}
