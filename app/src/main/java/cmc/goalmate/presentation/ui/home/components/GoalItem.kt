package cmc.goalmate.presentation.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.components.ParticipationStatusTag
import cmc.goalmate.presentation.components.TagSize
import cmc.goalmate.presentation.components.TextTag
import cmc.goalmate.presentation.components.TextTagSize
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Secondary02_700
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.home.GoalUiModel
import cmc.goalmate.presentation.ui.home.GoalUiStatus

@Composable
fun GoalItem(
    goal: GoalUiModel,
    navigateToDetail: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable { navigateToDetail(goal.id) },
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GoalThumbnail(goalUiStatus = goal.state)
        Text(
            text = goal.title,
            style = MaterialTheme.goalMateTypography.subtitleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Start),
        )
        ParticipationStatusTag(
            remainingCount = (goal.maxMembers - goal.currentMembers),
            participantsCount = goal.currentMembers,
            tagSize = TagSize.SMALL,
            goalUiStatus = goal.state,
            modifier = Modifier.align(Alignment.Start),
        )
        ClosingSoonLabel(modifier = Modifier.align(Alignment.Start))
    }
}

@Composable
fun ClosingSoonLabel(modifier: Modifier = Modifier) {
    TextTag(
        text = "마감임박",
        textColor = MaterialTheme.goalMateColors.background,
        backgroundColor = Secondary02_700,
        textStyle = MaterialTheme.goalMateTypography.captionMedium,
        size = TextTagSize.MEDIUM,
        modifier = modifier,
    )
}

@Composable
private fun GoalThumbnail(
    goalUiStatus: GoalUiStatus,
    modifier: Modifier = Modifier,
) {
    val imageModifier = Modifier.run {
        if (goalUiStatus == GoalUiStatus.SOLD_OUT) {
            this.alpha(0.2f)
        } else {
            this
        }
    }

    Box(
        modifier = modifier.height(117.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (goalUiStatus == GoalUiStatus.SOLD_OUT) {
            GoalMateImage(
                image = R.drawable.image_sold_out,
                modifier = Modifier,
            )
        }
        GoalMateImage(
            image = R.drawable.image_goal_default,
            modifier = imageModifier,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalItemPreview() {
    GoalMateTheme {
        val dummyGoal = GoalUiModel(
            id = 12,
            title = "(멘토명)과 함께하는 (목표명) 목표",
            imageUrl = "",
            price = "20,000원",
            discount = "100%",
            totalPrice = "0원",
            currentMembers = 0,
            maxMembers = 10,
            state = GoalUiStatus.AVAILABLE,
        )
        GoalItem(
            goal = dummyGoal,
            navigateToDetail = {},
            modifier = Modifier.width(GoalMateDimens.GoalItemWidth),
        )
    }
}
