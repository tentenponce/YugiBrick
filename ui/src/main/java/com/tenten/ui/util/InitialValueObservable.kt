package com.tenten.ui.util

import io.reactivex.Observable
import io.reactivex.Observer

/**
 *
 * Created by Exequiel Egbert V. Ponce on 4/13/2020.
 */
abstract class InitialValueObservable<T> : Observable<T>() {
    protected abstract val initialValue: T

    override fun subscribeActual(observer: Observer<in T>) {
        subscribeListener(observer)
        observer.onNext(initialValue)
    }

    protected abstract fun subscribeListener(observer: Observer<in T>)

    fun skipInitialValue(): Observable<T> = Skipped()

    private inner class Skipped : Observable<T>() {
        override fun subscribeActual(observer: Observer<in T>) {
            subscribeListener(observer)
        }
    }
}
