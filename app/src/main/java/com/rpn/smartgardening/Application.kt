package com.rpn.smartgardening

import android.app.Application
import com.rpn.adminmosque.repository.di.repositoryModule
import com.rpn.smartgardening.ui.viewmodel.viewModelModule
import com.rpn.smartgardening.utils.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {


    override fun onCreate() {
        super.onCreate()

        val modules = listOf(
            viewModelModule,
            repositoryModule,
            utilsModule
        )
        startKoin {
            androidContext(this@Application)
            modules(modules)
        }
    }

    companion object {
        const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
    }
}

