package com.meseems.mereach.server.domain.usecase

import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.server.domain.repository.ReachabilityService
import io.reactivex.Observable
import timber.log.Timber

/**
 * CheckReachabilityUseCase
 *
 * Used to check the Server connectivity and return it in a Result object
 * @param reachabilityService repository to check the Server connectivity
 */
class CheckReachabilityUseCase constructor(private val reachabilityService: ReachabilityService) {

    sealed class Result {
        object Loading : Result()
        data class Success(val servers: List<Server>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(servers: List<Server>): Observable<Result> {

        return Observable.fromIterable(servers)
            .flatMap { server ->
                reachabilityService.isReachable(server.serverUrl)
                    .toObservable()
                    .map { isOnline ->
                        server.apply {
                            online = isOnline
                        }
                    }
            }
            .toList()
            .toObservable()
            .doOnNext {
                Timber.d("list ${it.size}")
            }
            .map { Result.Success(it) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }
}
