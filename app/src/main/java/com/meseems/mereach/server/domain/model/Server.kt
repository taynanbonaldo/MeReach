package com.meseems.mereach.server.domain.model

class Server(
    var id: Long,
    val name: String,
    var online: Boolean = false,
    val serverUrl: String,
    val createdAt: String
)