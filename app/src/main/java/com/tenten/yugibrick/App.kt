package com.tenten.yugibrick

import androidx.multidex.MultiDexApplication
import com.tenten.yugibrick.di.appModule
import com.tenten.yugibrick.di.dataModule
import com.tenten.yugibrick.di.domainModule
import org.koin.android.ext.android.startKoin

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, dataModule, domainModule))
    }
}
