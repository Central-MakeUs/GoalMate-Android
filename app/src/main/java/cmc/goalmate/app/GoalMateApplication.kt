package cmc.goalmate.app

import android.app.Application
import cmc.goalmate.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GoalMateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(
            this,
            BuildConfig.KAKAO_NATIVE_APP_KEY,
        )
    }
}
