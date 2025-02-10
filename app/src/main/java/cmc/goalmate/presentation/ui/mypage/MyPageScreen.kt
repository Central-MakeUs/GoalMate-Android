package cmc.goalmate.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cmc.goalmate.R
import cmc.goalmate.presentation.components.HeaderTitle

@Composable
fun MyPageScreen() {
    Column {
        HeaderTitle(
            title = stringResource(R.string.my_page_title),
            modifier = Modifier.fillMaxWidth(),
        )
        MyPageContent(editNickName = {}, modifier = Modifier.fillMaxSize())
    }
}

data class MyPageUiModel(
    val nickName: String,
    val onGoingGoalCount: Int,
    val completedGoalCount: Int,
)
