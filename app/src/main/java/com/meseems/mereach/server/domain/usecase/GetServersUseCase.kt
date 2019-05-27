package com.meseems.mereach.server.domain.usecase

import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.server.domain.repository.ServerRepository
import io.reactivex.Observable

/**
 * GetServersUseCase
 *
 * Used to retrieve the servers and return it in a Result object
 * Additional business logic related to the servers can be performed here
 * @param ServerRepository repository to get the servers from data base
 */
class GetServersUseCase constructor(private val ServerRepository: ServerRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val servers: List<Server>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(): Observable<Result> {
        return ServerRepository.getServers()
                .toObservable()
                .map { Result.Success(it) as Result }
                .onErrorReturn { Result.Failure(it) }
                .startWith(Result.Loading)
    }
}
