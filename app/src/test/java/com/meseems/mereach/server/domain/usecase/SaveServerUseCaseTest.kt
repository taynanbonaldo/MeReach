package com.meseems.mereach.server.domain.usecase

import com.meseems.mereach.server.domain.model.NewServer
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
class SaveServerUseCaseTest{

    @Mock
    private lateinit var serverRepository: ServerRepository

    private lateinit var newServer: NewServer
    private lateinit var server: Server

    private lateinit var sut: SaveServerUseCase

    @Before
    fun setUp() {
        sut = SaveServerUseCase(serverRepository)
        newServer = NewServer("", "")
        server = Server(1, "", false, "","")
    }

    @Test
    fun `save server` (){
        given(serverRepository.insertNewServer(newServer)).willReturn(Single.just(1))

        sut.execute(newServer).test()

        verify(serverRepository).insertNewServer(newServer)
    }

    @Test
    fun `returns success result when success server saved` () {
        given(serverRepository.insertNewServer(newServer)).willReturn(Single.just(1))
        given(serverRepository.getServer(1)).willReturn(Single.just(server))

        sut.execute(newServer).test()
            .assertValueAt(1) {
                (it as SaveServerUseCase.Result.Success).server == server
            }
    }

    @Test
    fun `returns failure result when error saving server`() {
        val throwable = Throwable()

        given(serverRepository.insertNewServer(newServer)).willReturn(Single.error(throwable))

        sut.execute(newServer).test()
            .assertValueAt(1){
                (it as SaveServerUseCase.Result.Failure).throwable == throwable
            }
    }

}