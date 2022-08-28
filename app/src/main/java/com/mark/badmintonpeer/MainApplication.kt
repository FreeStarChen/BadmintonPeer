package com.mark.badmintonpeer

import android.app.Application
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.util.ServiceLocator
import kotlin.properties.Delegates
import timber.log.Timber

class MainApplication : Application() {

    val repository: BadmintonPeerRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: MainApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun isLiveDataDesign() = true
}
