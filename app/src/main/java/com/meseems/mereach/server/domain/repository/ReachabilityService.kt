package com.meseems.mereach.server.domain.repository

import io.reactivex.Single

interface ReachabilityService {
    fun isReachable(serverUrl: String): Single<Boolean>
}