package com.meseems.mereach.server.domain.usecase

import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.server.domain.repository.ServerRepository
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetServersUseCaseTest {
    
    @Mock
    lateinit var serverRepository: ServerRepository

    private lateinit var sut: GetServersUseCase

    @Before
    fun setUp() {
        sut = GetServersUseCase(serverRepository)
    }

    @Test
    fun `retrieve the server list`(){
        // Given
        given(serverRepository.getServers()).willReturn(Single.error(Throwable()))

        // When
        sut.execute().test()

        // Then
        verify(serverRepository).getServers()
    }

    @Test
    fun `returns the success result when success retrieving the server list`() {
        val serverList = arrayListOf<Server>()

        // Given
        given(serverRepository.getServers()).willReturn(Single.just(serverList))

        // When
        sut.execute().test()
            // Then
            .assertValueAt(1) {
                (it as GetServersUseCase.Result.Success).servers == serverList
            }
    }

    @Test
    fun `returns the failure result when error retrieving the server list`() {
        val throwable = Throwable()

        // Given
        given(serverRepository.getServers()).willReturn(Single.error(throwable))

        // When
        sut.execute().test()
            // Then
            .assertValueAt(1) {
                (it as GetServersUseCase.Result.Failure).throwable == throwable
            }
    }
}