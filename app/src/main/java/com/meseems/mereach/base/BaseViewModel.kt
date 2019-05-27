package com.meseems.mereach.base

import androidx.databinding.ObservableBoolean
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

open class BaseViewModel {

    protected var internalErrorSubject: PublishSubject<Int> = PublishSubject.create()
    protected var externalErrorSubject: PublishSubject<String> = PublishSubject.create()
    protected var loadingSubject: PublishSubject<Boolean> = PublishSubject.create()

    val internalErrorObservable: Observable<Int> = internalErrorSubject
    val externalErrorObservable: Observable<String> = externalErrorSubject
    val loadingObservable: Observable<Boolean> = loadingSubject

    val loading = ObservableBoolean(false)

    fun showError(resId: Int){
        internalErrorSubject.onNext(resId)
    }

    fun showApiError(message: String){
        externalErrorSubject.onNext(message)
    }
}