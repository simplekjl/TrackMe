package com.simplekjl.trackme

import android.app.Application
import com.facebook.stetho.Stetho
import com.simplekjl.data.di.dataModule
import com.simplekjl.domain.di.domainModule
import com.simplekjl.trackme.di.mainModule
import com.simplekjl.trackme.di.workerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        //start koin
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    mainModule,
                    workerModule
                )
            )
        }
    }
}