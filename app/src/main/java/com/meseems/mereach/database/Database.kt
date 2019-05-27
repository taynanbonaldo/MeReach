package com.meseems.mereach.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meseems.mereach.server.data.server.local.ServerLocal
import com.meseems.mereach.server.data.server.local.ServerDao

@Database(entities = arrayOf(
    ServerLocal::class
), version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serverDao(): ServerDao
}
