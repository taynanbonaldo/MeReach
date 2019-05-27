package com.meseems.mereach.server.data.reachability

import com.meseems.mereach.server.domain.repository.ReachabilityService
import io.reactivex.Single
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class ReachabilityServiceImpl : ReachabilityService {

    override fun isReachable(serverUrl: String): Single<Boolean> =
        Single.fromCallable {
            checkReachability(serverUrl, TIMEOUT)
        }

    private fun checkReachability(url: String, timeout: Int): Boolean {

        // Tip: You could use InetAddress, but is not reliable according to this post:
        // http://stackoverflow.com/questions/9922543/why-does-inetaddress-isreachable-return-false-when-i-can-ping-the-ip-address
        // What about an implementation using sockets?
        // // Using example in: http://stackoverflow.com/questions/9922543/why-does-inetaddress-isreachable-return-false-when-i-can-ping-the-ip-address
        // return url.contains("google.com.br") || url.contains("www.meseems.com.br")

        return try {
            Socket().use { soc ->
                soc.connect(InetSocketAddress(url, PORT), timeout)
            }
            true
        } catch (ex: IOException) {
            false
        }
    }

    companion object {
        private const val TIMEOUT = 1000
        private const val PORT = 443
    }
}