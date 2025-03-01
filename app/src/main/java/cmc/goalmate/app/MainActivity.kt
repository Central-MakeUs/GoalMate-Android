package cmc.goalmate.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
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

    private var backKeyPressedTime = 0L

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - backKeyPressedTime > 2000L) {
                backKeyPressedTime = System.currentTimeMillis()

                Toast.makeText(this@MainActivity, "한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            } else {
                finishAffinity()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            !mainViewModel.isReady.value
        }
        mainViewModel.checkLoginStatus()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        enableEdgeToEdge()
        setContent {
            GoalMateTheme {
                GoalMateScreen()
            }
        }
    }
}
