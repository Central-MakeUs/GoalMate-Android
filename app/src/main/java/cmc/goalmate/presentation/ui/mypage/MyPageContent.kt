package cmc.goalmate.presentation.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import cmc.goalmate.presentation.ui.mypage.components.GoalCountItem
import cmc.goalmate.presentation.ui.mypage.components.MenuItem
import cmc.goalmate.presentation.ui.mypage.components.ProfileHeaderSection
import cmc.goalmate.presentation.ui.mypage.model.MenuItemUiModel
import cmc.goalmate.presentation.ui.mypage.model.MyPageUiModel

@Composable
fun MyPageContent(
    userState: MyPageUiState,
    menuItems: List<MenuItemUiModel>,
    navigateToMyGoals: () -> Unit,
    onAction: (MenuAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Spacer(Modifier.size(16.dp))

        UserInfoSection(
            state = userState,
            editNickName = { onAction(MenuAction.EditNickName) },
            onGoalSummaryClicked = { navigateToMyGoals() },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )

        Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))
        ThickDivider()
        Spacer(Modifier.size(GoalMateDimens.HorizontalPadding))

        menuItems.forEach { menuItem ->
            MenuItem(
                title = menuItem.title,
                onClick = { onAction(menuItem.menuAction) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun UserInfoSection(
    state: MyPageUiState,
    editNickName: () -> Unit,
    onGoalSummaryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is MyPageUiState.Success -> {
            val userInfo = state.userInfo
            Column(modifier = modifier) {
                ProfileHeaderSection(
                    title = userInfo.nickName,
                    subtitle = userInfo.welcomeMessage,
                    targetIcon = userInfo.icon,
                    iconBackground = if (state.isLoggedIn) {
                        MaterialTheme.goalMateColors.completed
                    } else {
                        MaterialTheme.goalMateColors.primary
                    },
                    onIconClicked = editNickName,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))

                GoalStatusSummary(
                    onGoingGoalCount = userInfo.onGoingGoalCount,
                    completedGoalCount = userInfo.completedGoalCount,
                    onSectionClicked = onGoalSummaryClicked,
                    modifier = Modifier,
                )
            }
        }

        MyPageUiState.Error -> {}
        MyPageUiState.Loading -> {}
    }
}

@Composable
private fun GoalStatusSummary(
    onGoingGoalCount: Int,
    completedGoalCount: Int,
    onSectionClicked: () -> Unit,
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
                .clickable { onSectionClicked() }
                .background(
                    color = MaterialTheme.goalMateColors.primaryVariant,
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.goalMateColors.completed,
                    RoundedCornerShape(20.dp),
                )
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
            userState = MyPageUiState.Success(
                userInfo = MyPageUiModel.DEFAULT_INFO,
                isLoggedIn = false,
            ),
            menuItems = MenuItemUiModel.entries,
            navigateToMyGoals = {},
            onAction = {},
            modifier = Modifier.background(White),
        )
    }
}
