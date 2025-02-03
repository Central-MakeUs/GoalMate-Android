package cmc.goalmate.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cmc.goalmate.R
import cmc.goalmate.presentation.components.HeaderTitle

@Composable
fun MyPageScreen() {
    MyPageContent(modifier = Modifier.fillMaxSize())
}

@Composable
fun MyPageContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        HeaderTitle(title = stringResource(R.string.my_page_title))
    }
}
