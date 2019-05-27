package com.meseems.mereach.server.data.server.mapper

import com.meseems.mereach.server.data.server.local.ServerLocal
import com.meseems.mereach.server.domain.model.NewServer
import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.utils.extensions.date.getTimestampFormatted
import java.util.*

fun ServerLocal.toServer() : Server {
    return Server(
        id = this.id ?: 0,
        name = this.name,
        serverUrl = this.serverUrl,
        createdAt = this.createdAt
    )
}

fun Server.toServerLocal() : ServerLocal {
    return ServerLocal(
        id = this.id,
        name = this.name,
        serverUrl = this.serverUrl,
        createdAt = this.createdAt
    )
}

fun NewServer.toServerLocal() : ServerLocal {
    return ServerLocal(
        name = name,
        serverUrl = url,
        createdAt = Calendar.getInstance().getTimestampFormatted()
    )
}