package com.untilled.roadcapture.core.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.dao.LocalOAuthTokenDao
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class OAuthRefreshTokenWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val localOAuthTokenDao: LocalOAuthTokenDao,
    private val oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>,
) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        //handle your work here

        return try {
            Logger.d("Worker Successful")
            oauthLoginManagerMap[localOAuthTokenDao.getToken().socialType]?.validateToken()
            Result.retry()
        } catch (e: Exception) {
            Logger.e("Worker Exception $e")
            Result.failure()
        }
    }
}