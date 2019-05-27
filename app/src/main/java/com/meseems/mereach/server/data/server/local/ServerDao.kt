package com.meseems.mereach.server.data.server.local

import androidx.room.Dao
import androidx.room.Query
import com.meseems.mereach.database.BaseDao
import io.reactivex.Single

@Dao
abstract class ServerDao : BaseDao<ServerLocal>() {

    @Query("SELECT * FROM servers")
    abstract fun getServers(): Single<List<ServerLocal>>

    @Query("SELECT * FROM servers WHERE id LIKE :id LIMIT 1")
    abstract fun getServer(id: Long): Single<List<ServerLocal>>

}
