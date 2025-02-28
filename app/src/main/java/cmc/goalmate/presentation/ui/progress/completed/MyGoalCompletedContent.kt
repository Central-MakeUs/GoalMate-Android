package cmc.goalmate.presentation.ui.progress.completed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalDateRange
import cmc.goalmate.presentation.components.MoreOptionButton
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.detail.components.InfoRow
import cmc.goalmate.presentation.ui.detail.components.SubTitleText
import cmc.goalmate.presentation.ui.progress.completed.model.CompletedGoalUiModel
import cmc.goalmate.presentation.ui.progress.components.CompletedProgressBar
import cmc.goalmate.presentation.ui.progress.components.FinalMessage
import cmc.goalmate.presentation.ui.progress.components.Subtitle

@Composable
fun MyGoalCompletedContent(
    completedGoal: CompletedGoalUiModel,
    navigateToGoalDetail: () -> Unit,
    navigateToCommentDetail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        MoreOptionButton(
            label = "목표 상세보기",
            onClick = navigateToGoalDetail,
            modifier = Modifier.align(Alignment.End),
        )
        GoalInfoOverview(
            goal = completedGoal,
        )
        CompletedAchievementProgress(
            achievementProgress = completedGoal.achievementProgress,
        )
        CommentSection(
            nickName = completedGoal.mentee,
            mentor = completedGoal.mentor,
            comment = completedGoal.finalComment,
            navigateToMoreComments = navigateToCommentDetail,
        )
        Spacer(Modifier.size(156.dp))
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
                startDate = goal.formattedStartDate,
                endDate = goal.formattedEndDate,
                icon = R.drawable.icon_calendar_active,
            )
        }
    }
}

@Composable
private fun CompletedAchievementProgress(
    achievementProgress: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(22.dp),
    ) {
        SubTitleText(
            text = "달성율",
            modifier = Modifier
                .defaultMinSize(minWidth = 60.dp)
                .padding(top = 12.dp),
        )
        CompletedProgressBar(progress = achievementProgress)
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
            completedGoal = CompletedGoalUiModel.DUMMY,
            {},
            {},
            modifier = Modifier.background(White),
        )
    }
}
