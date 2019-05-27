package com.meseems.mereach.server.di

import com.meseems.mereach.base.BaseViewModel
import com.meseems.mereach.database.AppDatabase
import com.meseems.mereach.database.DatabaseModule.Companion.APP_DATABASE
import com.meseems.mereach.server.data.reachability.ReachabilityServiceImpl
import com.meseems.mereach.server.data.server.ServerRepositoryImpl
import com.meseems.mereach.server.data.server.local.ServerDao
import com.meseems.mereach.server.domain.repository.ReachabilityService
import com.meseems.mereach.server.domain.repository.ServerRepository
import com.meseems.mereach.server.domain.usecase.CheckReachabilityUseCase
import com.meseems.mereach.server.domain.usecase.DeleteServerUseCase
import com.meseems.mereach.server.domain.usecase.GetServersUseCase
import com.meseems.mereach.server.domain.usecase.SaveServerUseCase
import com.meseems.mereach.server.view.ServerViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ServerModule {

    val module = Kodein.Module("ServerModule") {

        bind<ServerDao>(SERVER_DAO) with provider {
            val database : AppDatabase = instance(APP_DATABASE)
            database.serverDao()
        }

        bind<ServerRepository>(SERVER_REPOSITORY) with provider {
            ServerRepositoryImpl(serverDao = instance(SERVER_DAO))
        }

        bind<ReachabilityService>(REACHABILITY_SERVICE) with provider {
            ReachabilityServiceImpl()
        }

        bind<GetServersUseCase>(GET_SERVERS_USE_CASE) with provider {
            GetServersUseCase(instance(SERVER_REPOSITORY))
        }

        bind<SaveServerUseCase>(SAVE_SERVER_USE_CASE) with provider {
            SaveServerUseCase(instance(SERVER_REPOSITORY))
        }

        bind<DeleteServerUseCase>(DELETE_SERVER_USE_CASE) with provider {
            DeleteServerUseCase(instance(SERVER_REPOSITORY))
        }

        bind<CheckReachabilityUseCase>(CHECK_REACHABILITY_USE_CASE) with provider {
            CheckReachabilityUseCase(instance(REACHABILITY_SERVICE))
        }

        bind<BaseViewModel>(SERVER_VIEW_MODEL) with singleton {
            ServerViewModel(
                getServerUseCase = instance(GET_SERVERS_USE_CASE),
                saveServerUseCase = instance(SAVE_SERVER_USE_CASE),
                deleteServerUseCase = instance(DELETE_SERVER_USE_CASE),
                checkReachabilityUseCase = instance(CHECK_REACHABILITY_USE_CASE)
            )
        }

    }

    companion object {
        const val SERVER_DAO = "SERVER_DAO"

        const val SERVER_REPOSITORY = "SERVER_REPOSITORY"
        const val REACHABILITY_SERVICE = "REACHABILITY_SERVICE"

        const val GET_SERVERS_USE_CASE = "GET_SERVERS_USE_CASE"
        const val SAVE_SERVER_USE_CASE = "SAVE_SERVER_USE_CASE"
        const val DELETE_SERVER_USE_CASE = "DELETE_SERVER_USE_CASE"
        const val CHECK_REACHABILITY_USE_CASE = "CHECK_REACHABILITY_USE_CASE"

        const val SERVER_VIEW_MODEL = "SERVER_VIEW_MODEL"

    }

}