package cmc.goalmate.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import cmc.goalmate.presentation.components.LogoAppBar
import cmc.goalmate.presentation.theme.GoalMateTheme

@Composable
fun HomeScreen() {
    Column {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "HomeScreen")
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    GoalMateTheme {
        HomeScreen()
    }
}
