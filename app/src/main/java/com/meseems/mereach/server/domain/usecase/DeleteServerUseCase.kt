package com.meseems.mereach.server.domain.usecase

import com.meseems.mereach.server.domain.model.NewServer
import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.server.domain.repository.ServerRepository
import io.reactivex.Observable

/**
 * SaveServerUseCase
 *
 * Used to delete the ServerLocal and return it in a Result object
 * Additional business logic related to the servers can be performed here
 * @param ServerRepository repository to remove the ServerLocal from data base
 */
class DeleteServerUseCase constructor(private val serverRepository: ServerRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val server: Server) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(server: Server): Observable<Result> {
        return serverRepository.deleteServer(server)
            .toObservable()
            .map { Result.Success(it) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }
}
