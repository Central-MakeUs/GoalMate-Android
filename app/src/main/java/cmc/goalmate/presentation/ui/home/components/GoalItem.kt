package cmc.goalmate.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.components.ParticipationStatusTag
import cmc.goalmate.presentation.components.PriceContent
import cmc.goalmate.presentation.components.PriceContentStyleSize
import cmc.goalmate.presentation.components.TagSize
import cmc.goalmate.presentation.components.TextTag
import cmc.goalmate.presentation.components.TextTagSize
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Secondary02_700
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.home.GoalState
import cmc.goalmate.presentation.ui.home.GoalUiModel

@Composable
fun GoalItem(
    goal: GoalUiModel,
    navigateToDetail: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.width(GoalMateDimens.GoalItemWidth).clickable { navigateToDetail(goal.id) },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        GoalThumbnail(goalState = goal.state)
        Text(
            text = goal.title,
            style = MaterialTheme.goalMateTypography.subtitleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        ParticipationStatusTag(
            remainingCount = (goal.maxMembers - goal.currentMembers),
            participantsCount = goal.currentMembers,
            tagSize = TagSize.SMALL,
            goalState = goal.state,
        )
        ClosingSoonLabel()
        PriceContent(
            discount = goal.discount,
            price = goal.price,
            totalPrice = goal.totalPrice,
            size = PriceContentStyleSize.SMALL,
            discountTextColor = MaterialTheme.goalMateColors.secondary01,
        )
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
    goalState: GoalState,
    modifier: Modifier = Modifier,
) {
    val imageModifier = Modifier.run {
        if (goalState == GoalState.SOLD_OUT) {
            this.alpha(0.2f)
        } else {
            this
        }
    }

    Box(
        modifier = modifier.size(width = GoalMateDimens.GoalItemWidth, height = 117.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (goalState == GoalState.SOLD_OUT) {
            GoalMateImage(
                image = R.drawable.image_sold_out,
                modifier = Modifier,
            )
        }
        GoalMateImage(
            image = R.drawable.goal_default_image,
            modifier = imageModifier,
        )
    }
}

@Composable
@Preview
private fun GoalItemPreview() {
    GoalMateTheme {
        val dummyGoal = GoalUiModel(
            id = 12L,
            title = "(멘토명)과 함께하는 (목표명) 목표",
            imageUrl = "",
            price = "20,000원",
            discount = "100%",
            totalPrice = "0원",
            currentMembers = 0,
            maxMembers = 10,
            state = GoalState.AVAILABLE,
        )
        GoalItem(
            goal = dummyGoal,
            navigateToDetail = {},
            modifier = Modifier.background(Color.White),
        )
    }
}
