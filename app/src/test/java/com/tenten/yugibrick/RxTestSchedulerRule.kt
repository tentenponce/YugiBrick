package com.tenten.yugibrick

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor

class RxTestSchedulerRule : TestRule {

    val testScheduler = TestScheduler()
    val immediateScheduler: Scheduler = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
        }
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { scheduler -> immediateScheduler }
                RxJavaPlugins.setNewThreadSchedulerHandler { scheduler -> immediateScheduler }
                RxJavaPlugins.setComputationSchedulerHandler { scheduler -> immediateScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { scheduler -> immediateScheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediateScheduler }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
