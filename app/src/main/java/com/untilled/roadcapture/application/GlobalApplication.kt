package com.untilled.roadcapture.application

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.kakao.sdk.common.KakaoSdk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.BuildConfig
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        Kotpref.init(this)
        KakaoSdk.init(this, BuildConfig.SOCIAL_NAVER_CLIENT_ID)
    }
}