package com.tenten.yugibrick.domain.base

import com.tenten.yugibrick.domain.common.executor.PostExecutionThread
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<Return, Param>(private val postExecutionThread: PostExecutionThread) {

    abstract fun buildObservable(param: Param): Single<Return>

    fun execute(param: Param): Single<Return> {
        return buildObservable(param)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
    }
}
