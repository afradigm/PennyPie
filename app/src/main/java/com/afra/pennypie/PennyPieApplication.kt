package com.afra.pennypie

import android.app.Application
import com.afra.pennypie.di.databaseModule
import com.afra.pennypie.di.useCaseModule
import com.afra.pennypie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PennyPieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PennyPieApplication)
            modules(
                databaseModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
} 