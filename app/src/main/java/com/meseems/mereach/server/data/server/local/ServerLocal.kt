package com.meseems.mereach.server.data.server.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servers")
data class ServerLocal(
    @PrimaryKey @ColumnInfo(name = "id") var id: Long ?= null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "server_url") val serverUrl: String,
    @ColumnInfo(name = "created_at") val createdAt: String
)