package cmc.goalmate.presentation.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import cmc.goalmate.presentation.ui.home.dummyGoals

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
        GoalThumbnail(goalUiStatus = goal.state, goalImage = goal.imageUrl)
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
        if (goal.isClosingSoon) {
            ClosingSoonLabel(modifier = Modifier.align(Alignment.Start))
        }
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
    goalImage: String,
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
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (goalUiStatus == GoalUiStatus.SOLD_OUT) {
            GoalMateImage(
                image = R.drawable.image_sold_out,
                modifier = Modifier.size(
                    width = GoalMateDimens.GoalItemWidth,
                    height = GoalMateDimens.GoalItemImageHeight,
                ),
            )
        }
        GoalMateImage(
            image = goalImage,
            modifier = imageModifier.size(
                width = GoalMateDimens.GoalItemWidth,
                height = GoalMateDimens.GoalItemImageHeight,
            ),
            shape = RoundedCornerShape(4.dp),
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalItemPreview() {
    GoalMateTheme {
        val dummyGoal = dummyGoals[0]
        GoalItem(
            goal = dummyGoal,
            navigateToDetail = {},
            modifier = Modifier.width(GoalMateDimens.GoalItemWidth),
        )
    }
}
