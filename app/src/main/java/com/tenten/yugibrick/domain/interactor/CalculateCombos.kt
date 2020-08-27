package com.tenten.yugibrick.domain.interactor

import com.tenten.yugibrick.domain.base.SingleUseCase
import com.tenten.yugibrick.domain.common.executor.PostExecutionThread
import com.tenten.yugibrick.domain.model.Combo
import com.tenten.yugibrick.domain.model.DeckCalculator
import com.tenten.yugibrick.domain.service.ProbabilityService
import io.reactivex.Single

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/28/2020.
 */
class CalculateCombos(
    postExecutionThread: PostExecutionThread,
    private val probabilityService: ProbabilityService
) : SingleUseCase<List<Combo>, DeckCalculator>(postExecutionThread) {

    override fun buildObservable(param: DeckCalculator): Single<List<Combo>> {
        return Single.just(param.combos.map { combo ->
            val probability = probabilityService.comboCalculator(
                cards = combo.cards,
                startingHand = param.handSize,
                deckCount = param.deckSize
            )

            combo.copy(probability = probability)
        })
    }
}
