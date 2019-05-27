package com.meseems.mereach.server.domain.usecase

import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.server.domain.repository.ServerRepository
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteServerUseCaseTest {

    @Mock
    lateinit var serverRepository: ServerRepository

    private lateinit var server: Server

    private lateinit var sut: DeleteServerUseCase


    @Before
    fun setUp() {
        sut = DeleteServerUseCase(serverRepository)
        server = Server(1, "", false, "","")
    }

    @Test
    fun `delete the server`(){
        // Given
        given(serverRepository.deleteServer(server)).willReturn(Single.just(server))

        // When
        sut.execute(server).test()

        // Then
        verify(serverRepository).deleteServer(server)
    }

    @Test
    fun `returns the success result when success deleting the server`() {
        // Given
        given(serverRepository.deleteServer(server)).willReturn(Single.just(server))

        // When
        sut.execute(server).test()
            // Then
            .assertValueAt(1) {
                (it as DeleteServerUseCase.Result.Success).server == server
            }
    }

    @Test
    fun `returns the failure result when error deleting the server`() {
        val throwable = Throwable()

        // Given
        given(serverRepository.deleteServer(server)).willReturn(Single.error(throwable))

        // When
        sut.execute(server).test()
            // Then
            .assertValueAt(1) {
                (it as DeleteServerUseCase.Result.Failure).throwable == throwable
            }
    }
}