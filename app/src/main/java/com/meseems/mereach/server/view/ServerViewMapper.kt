package com.meseems.mereach.server.view

import com.meseems.mereach.server.domain.model.Server
import io.reactivex.functions.Action

fun Server.toServerItemView(onClick: Action, onRemove: Action) : ServerItemView {
    return ServerItemView(
        id = this.id,
        name = this.name,
        serverUrl = this.serverUrl,
        createdAt = this.createdAt,
        online = this.online,
        onClick = onClick,
        onRemove = onRemove
    )
}
