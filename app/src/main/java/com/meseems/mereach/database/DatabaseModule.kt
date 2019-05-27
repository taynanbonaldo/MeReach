package com.meseems.mereach.database

import androidx.room.Room
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance

class DatabaseModule {

    val module = Kodein.Module("DatabaseModule") {

        bind<AppDatabase>(APP_DATABASE) with eagerSingleton {
            Room.databaseBuilder(instance(),
                AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    companion object {
        const val APP_DATABASE = "APP_DATABASE"
        private const val DATABASE_NAME = "mereach-database"
    }
}
