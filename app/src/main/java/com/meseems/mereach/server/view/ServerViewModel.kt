package com.meseems.mereach.server.view

import androidx.databinding.ObservableArrayList
import com.meseems.mereach.base.BaseViewModel
import com.meseems.mereach.server.domain.model.NewServer
import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.server.domain.usecase.CheckReachabilityUseCase
import com.meseems.mereach.server.domain.usecase.GetServersUseCase
import com.meseems.mereach.server.domain.usecase.DeleteServerUseCase
import com.meseems.mereach.server.domain.usecase.SaveServerUseCase
import com.meseems.mereach.utils.extensions.rx.applyIoToMainSchedulers
import com.meseems.mereach.utils.extensions.rx.applyLoading
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.processors.PublishProcessor
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ServerViewModel(
    private val getServerUseCase: GetServersUseCase,
    private val saveServerUseCase: SaveServerUseCase,
    private val deleteServerUseCase: DeleteServerUseCase,
    private val checkReachabilityUseCase: CheckReachabilityUseCase
) : BaseViewModel() {

    private val serversUpdateProcessor =
        PublishProcessor.create<Boolean>()

    private var addNewServerSubject: PublishSubject<Boolean> = PublishSubject.create()
    val addNewServerObservable: Observable<Boolean> = addNewServerSubject

    private var askToRemoveServerSubject: PublishSubject<Server> = PublishSubject.create()
    val askToRemoveServerObservable: Observable<Server> = askToRemoveServerSubject

    val serverList = ObservableArrayList<ServerItemView>()

    fun getServers(): Flowable<List<Server>> =
        serversUpdateProcessor
            .applyIoToMainSchedulers()
            .onBackpressureDrop()
            .concatMap { loadServers() }

    private fun loadServers() : Flowable<List<Server>>  =
        getServerUseCase.execute()
            .applyIoToMainSchedulers()
            .applyLoading(loading)
            .doOnNext { result ->
                when (result) {
                    is GetServersUseCase.Result.Failure -> {
                        result.throwable.printStackTrace()
                        externalErrorSubject.onNext("")
                    }
                }
            }
            .filter { it is GetServersUseCase.Result.Success }
            .map { it as GetServersUseCase.Result.Success }
            .doOnNext {
                updateServerList(it.servers)
            }
            .flatMap {
                checkReachability(it.servers)
            }
            .toFlowable(BackpressureStrategy.DROP)

    private fun checkReachability(servers: List<Server>) : Observable<List<Server>>  =
        checkReachabilityUseCase.execute(servers)
            .applyIoToMainSchedulers()
            .doOnNext { result ->
                when (result) {
                    is CheckReachabilityUseCase.Result.Failure -> {
                        result.throwable.printStackTrace()
                        externalErrorSubject.onNext("")
                    }
                }
            }
            .filter { it is CheckReachabilityUseCase.Result.Success }
            .map { (it as CheckReachabilityUseCase.Result.Success).servers }
            .doOnNext {
                updateServerList(it)
            }

    fun reachabilityRefresher() : Observable<Long> =
        Observable.interval(30, TimeUnit.SECONDS)
            .doOnSubscribe {
                serversUpdateProcessor.onNext(true)
            }
            .doOnNext {
                serversUpdateProcessor.onNext(true)
            }

    private fun updateServerList(servers: List<Server>) {
        if(servers.isNotEmpty()) {
            serverList.clear()
            serverList.addAll(buildServerItemViews(servers))
        }
    }

    private fun buildServerItemViews(servers: List<Server>) : List<ServerItemView> {
        return servers
            .map {
                // Map ServerLocal to ServerItemView
                it.toServerItemView(Action {
                    selectServer(it)
                }, Action {
                    askToRemoveServer(it)
                })
            }
    }

    fun onRefresh(){
        serversUpdateProcessor.onNext(true)
    }

    fun addNewServer() {
        addNewServerSubject.onNext(true)
    }

    private fun askToRemoveServer(server: Server) {
        askToRemoveServerSubject.onNext(server)
    }

    fun saveServer(name: String, url: String) : Observable<SaveServerUseCase.Result.Success>  =
        saveServerUseCase.execute(NewServer(name, url))
            .applyIoToMainSchedulers()
            .doOnNext { result ->
                when (result) {
                    is SaveServerUseCase.Result.Failure -> {
                        result.throwable.printStackTrace()
                        externalErrorSubject.onNext("")
                    }
                }
            }
            .filter { it is SaveServerUseCase.Result.Success }
            .map { it as SaveServerUseCase.Result.Success }
            .doOnNext { result ->
                serverList.add(
                    result.server.toServerItemView(Action {
                        selectServer(result.server)
                    }, Action {
                        askToRemoveServer(result.server)
                    })
                )
            }

    fun deleteServer(server: Server) : Observable<DeleteServerUseCase.Result.Success>  =
        deleteServerUseCase.execute(server)
            .applyIoToMainSchedulers()
            .doOnNext { result ->
                when (result) {
                    is DeleteServerUseCase.Result.Failure -> {
                        result.throwable.printStackTrace()
                        externalErrorSubject.onNext("")
                    }
                }
            }
            .filter { it is DeleteServerUseCase.Result.Success }
            .map { it as DeleteServerUseCase.Result.Success }
            .doOnNext { result ->
                removeServerFromList(result.server)
            }

    private fun removeServerFromList(server: Server) {
        val item = serverList.find { it.id == server.id }
        if(item != null) {
            serverList.remove(item)
        }
    }

    private fun selectServer(server: Server) {

    }
}
