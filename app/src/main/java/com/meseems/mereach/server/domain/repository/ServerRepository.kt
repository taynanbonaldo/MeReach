package com.meseems.mereach.server.domain.repository

import com.meseems.mereach.server.domain.model.NewServer
import com.meseems.mereach.server.domain.model.Server
import io.reactivex.Single

interface ServerRepository {

    fun getServers(): Single<List<Server>>

    fun insertNewServer(server: NewServer): Single<Long>

    fun deleteServer(server: Server): Single<Server>

    fun getServer(id: Long): Single<Server?>

}
