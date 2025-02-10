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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun MyPageContent(
    editNickName: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        NickNameSection(
            nickName = "김골메이트",
            onEditButtonClicked = editNickName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )

        Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))

        GoalStatusSummary(
            onGoingGoalCount = 2,
            completedGoalCount = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )

        Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))
        ThickDivider()
        Spacer(Modifier.size(GoalMateDimens.HorizontalPadding))

        MenuItem(
            title = "자주 묻는 질문",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )
        MenuItem(
            title = "개인 정보 처리 방침",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )
        MenuItem(
            title = "이용약관",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )
        MenuItem(
            title = "탈퇴하기",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = GoalMateDimens.HorizontalPadding),
        )
    }
}

@Composable
private fun NickNameSection(
    nickName: String,
    onEditButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.primary,
                shape = RoundedCornerShape(24.dp),
            )
            .padding(
                vertical = GoalMateDimens.BoxContentVerticalPadding,
                horizontal = GoalMateDimens.HorizontalPadding,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "$nickName 님",
                style = MaterialTheme.goalMateTypography.subtitleSmall,
                color = MaterialTheme.goalMateColors.onBackground,
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_edit),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.goalMateColors.completed,
                    )
                    .clickable { onEditButtonClicked() }
                    .padding(5.dp),
            )
        }
        Spacer(Modifier.size(GoalMateDimens.VerticalArrangementSpaceSmall))
        Text(
            "안녕하세요!\n" + "골메이트에 오신 것을 환영해요",
            style = MaterialTheme.goalMateTypography.body,
            color = MaterialTheme.goalMateColors.onBackground,
        )
    }
}

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
private fun GoalCountItem(
    count: String,
    tag: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        Text(
            text = count,
            style = MaterialTheme.goalMateTypography.subtitle,
            color = MaterialTheme.goalMateColors.onBackground,
        )
        Text(
            text = tag,
            style = MaterialTheme.goalMateTypography.body,
            color = MaterialTheme.goalMateColors.onSurface,
        )
    }
}

@Composable
private fun MenuItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.goalMateTypography.body,
        color = MaterialTheme.goalMateColors.onBackground,
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 11.dp),
    )
}

@Composable
@Preview(showBackground = true)
private fun MyPageContentPreview() {
    GoalMateTheme {
        MyPageContent(
            editNickName = {},
            modifier = Modifier.background(White),
        )
    }
}
