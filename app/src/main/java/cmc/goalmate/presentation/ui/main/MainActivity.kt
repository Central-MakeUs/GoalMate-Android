package cmc.goalmate.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import cmc.goalmate.presentation.theme.GoalMateTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoalMateTheme {
                MainScreen()
            }
        }
    }
}

sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object MyGoalsScreen : Screen()

    @Serializable
    data object MyPageScreen : Screen()
}
