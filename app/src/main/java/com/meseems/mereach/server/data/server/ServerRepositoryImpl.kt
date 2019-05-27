package com.meseems.mereach.server.data.server

import com.meseems.mereach.server.data.server.local.ServerDao
import com.meseems.mereach.server.data.server.mapper.toServer
import com.meseems.mereach.server.data.server.mapper.toServerLocal
import com.meseems.mereach.server.domain.model.NewServer
import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.server.domain.repository.ServerRepository
import io.reactivex.Single

class ServerRepositoryImpl(private val serverDao: ServerDao) : ServerRepository {

    override fun getServers(): Single<List<Server>> =
        serverDao.getServers().map { list ->
            list.map { it.toServer() }
        }

    override fun insertNewServer(server: NewServer): Single<Long> =
        Single.fromCallable {
            val serverLocal = server.toServerLocal()
            serverDao.insert(serverLocal)
        }

    override fun getServer(id: Long): Single<Server?> =
        serverDao.getServer(id)
            .map {
                if(it.isEmpty())
                    null
                else
                    it[0].toServer()
            }

    override fun deleteServer(server: Server): Single<Server> =
        Single.fromCallable {
            serverDao.delete(server.toServerLocal())
        }.map {
            server
        }

}
