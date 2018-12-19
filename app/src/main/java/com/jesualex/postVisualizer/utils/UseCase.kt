package com.jesualex.postVisualizer.utils

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by jesualex on 19-12-18.
 */

abstract class UseCase<T> {
    private val compositeDisposable = CompositeDisposable()
    private val subscribeOn: Scheduler get() = Schedulers.io()
    private val observeOn: Scheduler get() = AndroidSchedulers.mainThread()
    val observable: Observable<T> get() = createObservableUseCase()

    fun execute(disposableObserver: UseCaseObserver<T>?) {
        disposableObserver?.let {
            val observable = createObservableUseCase().subscribeOn(subscribeOn).observeOn(observeOn)
            val observer = observable.subscribeWith(disposableObserver)
            compositeDisposable.add(observer)
        }
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    protected abstract fun createObservableUseCase(): Observable<T>
}