package com.mark.badmintonpeer.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.mark.badmintonpeer.data.source.BadmintonPeerDataSource
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.data.source.DefaultBadmintonPeerRepository
import com.mark.badmintonpeer.data.source.local.BadmintonPeerLocalDataSource
import com.mark.badmintonpeer.data.source.remote.BadmintonPeerRemoteDataSource

/**
* A Service Locator for the [BadmintonPeerRepository].
*/
object ServiceLocator {

    @Volatile
    var repository: BadmintonPeerRepository? = null
    @VisibleForTesting set

    fun provideRepository(context: Context): BadmintonPeerRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createBadmintonPeerRepository(context)
        }
    }

    private fun createBadmintonPeerRepository(context: Context): BadmintonPeerRepository {
        return DefaultBadmintonPeerRepository(
            BadmintonPeerRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): BadmintonPeerDataSource {
        return BadmintonPeerLocalDataSource(context)
    }

}