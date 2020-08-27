package com.tenten.yugibrick.view.common.executor

import com.tenten.yugibrick.domain.common.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class PostExecutionThreadImpl : PostExecutionThread {
    override val scheduler: Scheduler = AndroidSchedulers.mainThread()
}
