package com.tenten.yugibrick.view.base

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val stateHasInternet = MutableLiveData<Boolean>()
    val stateErrorGetNetworkStatus = MutableLiveData<Throwable>()

    val disposables = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
