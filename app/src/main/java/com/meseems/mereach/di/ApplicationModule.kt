package com.meseems.mereach.di

import com.meseems.mereach.database.DatabaseModule
import com.meseems.mereach.server.di.ServerModule
import org.kodein.di.Kodein

class ApplicationModule {

    val module = Kodein.Module("ApplicationModule") {

        import(DatabaseModule().module)
        import(ServerModule().module)
    }

}
