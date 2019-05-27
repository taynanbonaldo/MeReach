package com.meseems.mereach

import android.app.Application
import com.meseems.mereach.di.ApplicationModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import timber.log.Timber

class BaseApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidCoreModule(this@BaseApplication))
        import(ApplicationModule().module)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
    }

}