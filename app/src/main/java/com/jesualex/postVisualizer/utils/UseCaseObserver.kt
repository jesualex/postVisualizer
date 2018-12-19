package com.jesualex.postVisualizer.utils

import io.reactivex.observers.DisposableObserver

/**
 * Created by jesualex on 19-12-18.
 */

abstract class UseCaseObserver<T> : DisposableObserver<T>() {
    override fun onNext(value: T) {}
    override fun onError(e: Throwable) { e.printStackTrace() }
    override fun onComplete() {}
}