package cmc.goalmate.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import cmc.goalmate.presentation.components.ParticipationStatusTag
import cmc.goalmate.presentation.components.TagSize
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.components.ThinDivider
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.detail.components.ImageSlider
import cmc.goalmate.presentation.ui.detail.components.InfoRow
import cmc.goalmate.presentation.ui.detail.components.MilestoneRowItem
import cmc.goalmate.presentation.ui.detail.components.SubTitleText
import cmc.goalmate.presentation.ui.detail.components.WeeklyRowItem
import cmc.goalmate.presentation.ui.home.GoalUiStatus
import cmc.goalmate.presentation.ui.home.components.ClosingSoonLabel

@Composable
fun GoalDetailContent(
    goal: GoalDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImageSlider(
            imageUrls = goal.imageUrls,
            modifier = Modifier.fillMaxWidth().height(270.dp),
        )

        GoalContent(
            goal = goal,
            modifier = Modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
        )

        ThickDivider()

        GoalList(
            title = stringResource(R.string.goal_detail_weekly_goals_title),
            goals = goal.weeklyGoal,
            modifier = Modifier.padding(
                vertical = GoalMateDimens.ItemVerticalPaddingLarge,
                horizontal = GoalMateDimens.HorizontalPadding,
            ),
        ) { mileStone, modifier ->
            WeeklyRowItem(
                label = mileStone.label,
                content = mileStone.content,
                modifier = modifier,
            )
        }

        ThickDivider()

        GoalList(
            title = stringResource(R.string.goal_detail_milestone_title),
            goals = goal.milestone,
            modifier = Modifier.padding(
                vertical = GoalMateDimens.ItemVerticalPaddingLarge,
                horizontal = GoalMateDimens.HorizontalPadding,
            ),
        ) { mileStone, modifier ->
            MilestoneRowItem(
                label = mileStone.label,
                content = mileStone.content,
                modifier = modifier,
            )
        }

        // TODO: 상세이미지 위치
        Spacer(Modifier.size(120.dp))
    }
}

@Composable
private fun GoalContent(
    goal: GoalDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        GoalHeader(
            goal = goal,
            modifier = Modifier.padding(vertical = GoalMateDimens.ItemVerticalPaddingSmall),
        )

        ThinDivider()

        GoalInfo(
            goal = goal,
            modifier = Modifier.padding(vertical = GoalMateDimens.ItemVerticalPaddingMedium),
        )

        ThinDivider()

        GoalDescription(
            description = goal.description,
            modifier = Modifier.padding(vertical = GoalMateDimens.ItemVerticalPaddingLarge),
        )
    }
}

@Composable
private fun GoalHeader(
    goal: GoalDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = goal.title,
            style = MaterialTheme.goalMateTypography.h5,
        )
        ParticipationStatusTag(
            remainingCount = goal.remainingCount,
            participantsCount = goal.currentMembers,
            tagSize = TagSize.LARGE,
            goalUiStatus = goal.state,
        )
        ClosingSoonLabel()
    }
}

@Composable
private fun GoalInfo(
    goal: GoalDetailUiModel,
    modifier: Modifier = Modifier,
) {
    val contentTextStyle = goalMateTypography.subtitleSmall

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(GoalMateDimens.InfoRowSpace),
    ) {
        InfoRow(
            title = stringResource(R.string.goal_detail_subject_title),
            content = goal.category,
            contentTextStyle = contentTextStyle,
        )
        InfoRow(
            title = stringResource(R.string.goal_detail_mentor_title),
            content = goal.mentorName,
            contentTextStyle = contentTextStyle,
        )
        InfoRow(
            title = stringResource(R.string.goal_detail_date_title),
            content = goal.totalDates,
            contentTextStyle = contentTextStyle,
        ) {
            GoalDateRange(
                startDate = goal.startDate,
                endDate = goal.endDate,
                icon = R.drawable.icon_calendar,
            )
        }
        InfoRow(
            title = stringResource(R.string.goal_detail_time),
            content = goal.time,
            contentTextStyle = contentTextStyle,
        )
    }
}

@Composable
private fun GoalDescription(
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        SubTitleText(
            text = stringResource(R.string.goal_detail_description_title),
        )
        Spacer(modifier = Modifier.size(GoalMateDimens.TitleContentPadding))
        Text(
            text = description,
            style = MaterialTheme.goalMateTypography.subtitleSmall,
            color = MaterialTheme.goalMateColors.onBackground,
            modifier = Modifier
                .background(
                    color = MaterialTheme.goalMateColors.thickDivider,
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(20.dp),
        )
    }
}

@Composable
fun GoalList(
    title: String,
    goals: List<Milestone>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (Milestone, Modifier) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        SubTitleText(
            text = title,
        )
        Spacer(modifier = Modifier.size(GoalMateDimens.TitleContentPadding))

        goals.forEachIndexed { index, goal ->
            val isLast = (goals.size - 1) == index
            itemContent(goal, Modifier.padding(bottom = (if (isLast) 0.dp else 10.dp)))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalDetailScreenPreview() {
    GoalMateTheme {
        GoalDetailContent(
            goal = GoalDetailUiModel(
                title = "다온과 함께하는 영어 완전 정복 30일 목표",
                mentorName = "다온",
                imageUrls = listOf("이미지1", "이미지2", "이미지3"),
                category = "영어",
                totalDates = "30일",
                startDate = "2025년 01월 01일",
                endDate = "2025년 01월 30일",
                time = "하루 평균 4시간",
                price = "10,000원",
                discount = "100%",
                totalPrice = "0원",
                currentMembers = 7,
                maxMembers = 23,
                state = GoalUiStatus.AVAILABLE,
                description = "“영어를 하고 싶었지만 어떤 방법으로 해야 할 지, 루틴을 세우지만 어떤 방법이 효율적일지 고민이 많지 않았나요?”",
                weeklyGoal = listOf(
                    Milestone("1주차", "간단한 단어부터 시작하기"),
                    Milestone("2주차", "기본 문장 읽기"),
                ),
                milestone = listOf(Milestone("1주차", "간단한 단어부터 시작하기"), Milestone("2주차", "기본 문장 읽기")),
                detailImageUrl = "",
            ),
            modifier = Modifier.background(White),
        )
    }
}
