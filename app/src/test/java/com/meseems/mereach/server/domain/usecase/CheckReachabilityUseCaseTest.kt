package com.meseems.mereach.server.domain.usecase

import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.server.domain.repository.ReachabilityService
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CheckReachabilityUseCaseTest {

    @Mock
    lateinit var reachabilityService: ReachabilityService

    lateinit var servers: List<Server>
    val serverUrl = ""

    lateinit var sut: CheckReachabilityUseCase

    @Before
    fun setUp() {
        sut = CheckReachabilityUseCase(reachabilityService)
        servers = getStubServers()
    }

    @Test
    fun `check isReachable for each Server`(){
        // Given
        given(reachabilityService.isReachable(serverUrl)).willReturn(Single.just(true))

        // When
        sut.execute(servers).test()

        // Then
        verify(reachabilityService, times(servers.size)).isReachable(serverUrl)
    }

    @Test
    fun `returns list items with online param equals true when Server isReachable`() {
        // Given
        given(reachabilityService.isReachable(serverUrl)).willReturn(Single.just(true))

        // When
        sut.execute(servers).test()
            // Then
            .assertValueAt(1) {
                (it as CheckReachabilityUseCase.Result.Success).servers[0].online
            }
    }

    @Test
    fun `returns the failure result when error on check isReachable`() {
        val throwable = Throwable()

        // Given
        given(reachabilityService.isReachable(serverUrl)).willReturn(Single.error(throwable))

        // When
        sut.execute(servers).test()
            // Then
            .assertValueAt(1) {
                (it as CheckReachabilityUseCase.Result.Failure).throwable == throwable
            }
    }


    private fun getStubServers(): List<Server> {
        val productsResp = ArrayList<Server>()
        for(i in 1..3){
            productsResp.add(Server(i.toLong(), "", false, "",""))
        }
        return productsResp
    }
}