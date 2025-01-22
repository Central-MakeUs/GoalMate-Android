package cmc.goalmate.presentation.ui.progress.completed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalDateRange
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateProgressBar
import cmc.goalmate.presentation.components.MoreOptionButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.detail.components.InfoRow
import cmc.goalmate.presentation.ui.detail.components.SubTitleText
import cmc.goalmate.presentation.ui.mygoals.MyGoalState
import cmc.goalmate.presentation.ui.progress.components.FinalMessage
import cmc.goalmate.presentation.ui.progress.inprogress.Subtitle

data class CompletedGoalUiModel(
    val title: String,
    val mentor: String,
    val period: String,
    val startDate: String,
    val endDate: String,
    val achievementProgress: Float,
    val finalComment: String,
) {
    companion object {
        val DUMMY = CompletedGoalUiModel(
            title = "마루와 함께하는 백엔드 서버 찐천재",
            mentor = "마루",
            period = "30일",
            startDate = "2025년 10월 19일",
            endDate = "2025년 11월 19일",
            achievementProgress = 80f,
            finalComment = "굳",
        )
    }
}

@Composable
fun MyGoalCompletedContent(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = GoalMateDimens.HorizontalPadding,
                vertical = GoalMateDimens.BottomMargin,
            ),
    ) {
        MoreOptionButton(
            label = "목표 상세보기",
            onClick = {},
            modifier = Modifier.align(Alignment.End),
        )
        GoalInfoOverview(
            goal = CompletedGoalUiModel.DUMMY,
        )
        CompletedAchievementProgress(
            achievementProgress = CompletedGoalUiModel.DUMMY.achievementProgress,
        )
        CommentSection(
            nickName = "마루",
            mentor = "다온",
            comment = CompletedGoalUiModel.DUMMY.finalComment,
            navigateToMoreComments = {},
        )
        Spacer(modifier = Modifier.weight(1f))
        GoalMateButton(
            content = "다음 목표 시작하기",
            onClick = {},
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun GoalInfoOverview(
    goal: CompletedGoalUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        InfoRow(
            title = "목표",
            content = goal.title,
        )
        InfoRow(
            title = "멘토",
            content = goal.mentor,
        )
        InfoRow(
            title = "진행 기간",
            content = goal.period,
        ) {
            GoalDateRange(
                startDate = goal.startDate,
                endDate = goal.endDate,
                icon = R.drawable.icon_calendar_active,
            )
        }

        // COMMENT 부분 추가
    }
}

@Composable
private fun CompletedAchievementProgress(
    achievementProgress: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        SubTitleText(
            text = "달성율",
            modifier = Modifier.defaultMinSize(minWidth = 60.dp),
        )

        GoalMateProgressBar(
            currentProgress = achievementProgress,
            myGoalState = MyGoalState.IN_PROGRESS,
            thickness = 14.dp,
        )
    }
}

@Composable
private fun CommentSection(
    nickName: String,
    mentor: String,
    comment: String,
    navigateToMoreComments: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        FinalMessage(
            mentee = nickName,
            mentor = mentor,
            message = comment,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = modifier
                .background(
                    color = MaterialTheme.goalMateColors.thinDivider,
                    shape = RoundedCornerShape(20.dp),
                )
                .fillMaxWidth()
                .padding(
                    vertical = GoalMateDimens.BottomMargin,
                    horizontal = GoalMateDimens.HorizontalPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Subtitle(title = stringResource(R.string.goal_progress_comment_title))
            MoreOptionButton(
                label = "자세히 보기",
                onClick = navigateToMoreComments,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun MyGoalCompletedContentPreview() {
    GoalMateTheme {
        MyGoalCompletedContent(
            modifier = Modifier.background(White),
        )
    }
}
