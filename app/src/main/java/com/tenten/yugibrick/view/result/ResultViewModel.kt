package com.tenten.yugibrick.view.result

import androidx.lifecycle.MutableLiveData
import com.tenten.yugibrick.view.base.BaseViewModel

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/28/2020.
 */
class ResultViewModel :
    BaseViewModel() {

    companion object {
        private const val LOW_THRESHOLD = 20
        private const val MID_THRESHOLD = 45
    }

    val statePercent = MutableLiveData<Float>()
    val stateResult = MutableLiveData<Pair<Float, Result>>()

    fun setProbability(probability: Float) {
        val percentage = 100 * probability

        statePercent.value = percentage

        if (percentage <= LOW_THRESHOLD) {
            stateResult.value = Pair(percentage, Result.LOW)
        } else if (percentage <= MID_THRESHOLD) {
            stateResult.value = Pair(percentage, Result.MID)
        } else {
            stateResult.value = Pair(percentage, Result.HIGH)
        }
    }
}
