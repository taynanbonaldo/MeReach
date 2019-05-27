package com.meseems.mereach.server.view

import io.reactivex.functions.Action

data class ServerItemView (
        var id: Long,
        val name: String,
        val serverUrl: String,
        val createdAt: String,
        var online: Boolean = false,
        val onClick: Action,
        val onRemove: Action
) {
        fun select() {
                onClick.run()
        }
        fun remove() {
                onRemove.run()
        }
        fun isOnline() = online
}

