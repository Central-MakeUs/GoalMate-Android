package cmc.goalmate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.ui.main.GoalMateScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !mainViewModel.isReady.value
        }
        mainViewModel.checkLoginStatus()

        enableEdgeToEdge()
        setContent {
            GoalMateTheme {
                GoalMateScreen()
            }
        }
    }
}
