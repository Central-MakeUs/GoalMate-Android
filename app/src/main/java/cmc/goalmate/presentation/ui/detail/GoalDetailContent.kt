package cmc.goalmate.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.components.PriceContent
import cmc.goalmate.presentation.components.PriceContentStyle
import cmc.goalmate.presentation.components.ThickDivider
import cmc.goalmate.presentation.components.ThinDivider
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Grey600
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.detail.components.InfoRow
import cmc.goalmate.presentation.ui.detail.components.MilestoneRowItem
import cmc.goalmate.presentation.ui.detail.components.SubTitleText
import cmc.goalmate.presentation.ui.detail.components.WeeklyRowItem
import cmc.goalmate.presentation.ui.home.GoalState

@Composable
private fun GoalDetailContent(
    goal: GoalDetailUiModel,
    showBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppBarWithBackButton(onBackButtonClicked = {})
        ImageSlider(
            imageUrls = goal.imageUrls,
            modifier = Modifier.size(width = 360.dp, height = 270.dp),
        )
        Column(
            modifier = Modifier.padding(horizontal = GoalMateDimens.HorizontalPadding),
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

            ThickDivider()

            GoalList(
                title = stringResource(R.string.goal_detail_weekly_goals_title),
                goals = goal.weeklyGoal,
                modifier = Modifier.padding(vertical = GoalMateDimens.ItemVerticalPaddingLarge),
            ) { goal, modifier ->
                WeeklyRowItem(
                    label = "1주",
                    content = goal,
                    modifier = modifier,
                )
            }

            ThickDivider()

            GoalList(
                title = stringResource(R.string.goal_detail_milestone_title),
                goals = goal.weeklyGoal,
                modifier = Modifier.padding(vertical = GoalMateDimens.ItemVerticalPaddingLarge),
            ) { goal, modifier ->
                MilestoneRowItem(
                    label = "1",
                    content = goal,
                    modifier = modifier,
                )
            }
        }
    }
//    GoalMateButton(
//        content = stringResource(R.string.goal_detail_start_button),
//        onClick = showBottomSheet,
//        modifier = Modifier.fillMaxWidth(),
//    )
}

@Composable
private fun ImageSlider(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text("이미지 부분", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun GoalHeader(
    goal: GoalDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = goal.title,
            style = MaterialTheme.goalMateTypography.h5,
        )
        PriceContent(
            discount = goal.discount,
            price = goal.price,
            totalPrice = goal.totalPrice,
            style = PriceContentStyle.Large,
        )
        // TODO: 뱃지 디자인
    }
}

@Composable
private fun GoalInfo(
    goal: GoalDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        InfoRow(
            title = stringResource(R.string.goal_detail_subject_title),
            content = goal.category,
        )
        InfoRow(
            title = stringResource(R.string.goal_detail_mentor_title),
            content = goal.mentorName,
        )
        InfoRow(
            title = stringResource(R.string.goal_detail_date_title),
            content = goal.totalDates,
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.goalMateColors.thickDivider,
                        shape = RoundedCornerShape(12.dp),
                    ).padding(12.dp),
            ) {
                GoalMateImage(image = R.drawable.icon_calendar, modifier = Modifier.size(24.dp))
                Spacer(Modifier.size(10.dp))
                Column {
                    Text(
                        text = goal.startDate,
                        style = MaterialTheme.goalMateTypography.bodySmallMedium,
                        color = Grey600,
                    )
                    Text(
                        text = goal.endDate,
                        style = MaterialTheme.goalMateTypography.bodySmallMedium,
                        color = Grey600,
                    )
                }
            }
        }
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
    goals: List<String>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (String, Modifier) -> Unit,
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
                imageUrls = listOf(),
                category = "영어",
                totalDates = "30일",
                startDate = "2025년 01월 01일",
                endDate = "2025년 01월 30일",
                price = "10,000원",
                discount = "100%",
                totalPrice = "0원",
                currentMembers = 7,
                maxMembers = 23,
                state = GoalState.AVAILABLE,
                description = "“영어를 하고 싶었지만 어떤 방법으로 해야 할 지, 루틴을 세우지만 어떤 방법이 효율적일지 고민이 많지 않았나요?”",
                weeklyGoal = listOf("간단한 단어부터 시작하기", "기본 문장 읽기"),
                milestone = listOf("영어로 원어민과 편안하게 대화하는 법"),
                detailImageUrl = "",
            ),
            showBottomSheet = {},
            modifier = Modifier.background(White),
        )
    }
}
