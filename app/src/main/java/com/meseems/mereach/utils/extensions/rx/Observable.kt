package com.meseems.mereach.utils.extensions.rx

import androidx.databinding.ObservableBoolean
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

fun <T> Flowable<T>.applyIoToMainSchedulers(): Flowable<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}


fun <T> Flowable<T>.applyLoading(showHideProgressSubject: PublishSubject<Boolean>): Flowable<T> {
    return doOnSubscribe { showHideProgressSubject.onNext(true) }
            .doAfterTerminate { showHideProgressSubject.onNext(false) }
}

fun <T> Observable<T>.applyIoToMainSchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applyIoToMainSchedulers(): Single<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applyLoading(showHideProgressSubject: PublishSubject<Boolean>): Observable<T> {
    return doOnSubscribe { showHideProgressSubject.onNext(true) }
        .doAfterTerminate { showHideProgressSubject.onNext(false) }
}

fun <T> Observable<T>.applyLoading(loading: ObservableBoolean): Observable<T> {
    return doOnSubscribe { loading.set(true) }
        .doAfterTerminate { loading.set(false) }
}