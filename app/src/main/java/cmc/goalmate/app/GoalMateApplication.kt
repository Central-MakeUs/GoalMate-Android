package cmc.goalmate.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GoalMateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
