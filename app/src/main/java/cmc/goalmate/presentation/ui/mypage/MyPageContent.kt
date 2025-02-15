package cmc.goalmate.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.common.UserState
import cmc.goalmate.presentation.ui.mypage.components.GoalCountItem
import cmc.goalmate.presentation.ui.mypage.components.LoginSection
import cmc.goalmate.presentation.ui.mypage.components.MenuItem
import cmc.goalmate.presentation.ui.mypage.components.NickNameSection
import cmc.goalmate.presentation.ui.mypage.model.MenuItemData
import cmc.goalmate.presentation.ui.mypage.model.MyPageUiModel

@Composable
fun MyPageContent(
    userState: UserState<MyPageUiModel>,
    menuItems: List<MenuItemData>,
    editNickName: () -> Unit,
    navigateToMyGoals: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (userState) {
            is UserState.LoggedIn -> {
                NickNameSection(
                    nickName = userState.data.nickName,
                    onEditButtonClicked = editNickName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = GoalMateDimens.HorizontalPadding),
                )
            }

            UserState.LoggedOut -> {
                LoginSection(
                    onLoginButtonClicked = navigateToLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = GoalMateDimens.HorizontalPadding),
                )
            }
        }

        Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))

        GoalStatusSummary(
            onGoingGoalCount = userState.loggedInOrDefault { it.onGoingGoalCount },
            completedGoalCount = userState.loggedInOrDefault { it.completedGoalCount },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )

        Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))
        ThickDivider()
        Spacer(Modifier.size(GoalMateDimens.HorizontalPadding))

        menuItems.forEach { (title, onClick) ->
            MenuItem(
                title = title,
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = GoalMateDimens.HorizontalPadding),
            )
        }
    }
}

private inline fun UserState<MyPageUiModel>.loggedInOrDefault(defaultValue: (MyPageUiModel) -> Int): Int =
    if (this is UserState.LoggedIn) defaultValue(this.data) else MyPageUiModel.DEFAULT_COUNT

@Composable
private fun GoalStatusSummary(
    onGoingGoalCount: Int,
    completedGoalCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(GoalMateDimens.VerticalArrangementSpaceSmall),
    ) {
        Text(
            "목표 현황",
            style = MaterialTheme.goalMateTypography.subtitleSmall,
            color = MaterialTheme.goalMateColors.onBackground,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    color = MaterialTheme.goalMateColors.primaryVariant,
                )
                .border(width = 1.dp, color = MaterialTheme.goalMateColors.completed)
                .padding(vertical = GoalMateDimens.BoxContentVerticalPadding),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            GoalCountItem(count = "$onGoingGoalCount", tag = "진행중")

            VerticalDivider(
                thickness = 1.dp,
                color = MaterialTheme.goalMateColors.completed,
                modifier = Modifier.height(51.dp),
            )

            GoalCountItem(count = "$completedGoalCount", tag = "진행완료")
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MyPageContentPreview() {
    GoalMateTheme {
        MyPageContent(
            userState = UserState.LoggedOut,
            menuItems = listOf(),
            editNickName = {},
            navigateToMyGoals = {},
            navigateToLogin = {},
            modifier = Modifier.background(White),
        )
    }
}
