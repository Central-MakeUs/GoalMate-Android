package cmc.goalmate.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GoalThumbnail(goalState = goal.state)
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = goal.title,
            style = MaterialTheme.goalMateTypography.subtitleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = goal.discount)
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = goal.price,
                style = TextStyle(
                    textDecoration = TextDecoration.LineThrough,
                ),
            )
        }
        Text(text = goal.totalPrice)
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_alram),
                contentDescription = stringResource(R.string.goal_available_members),
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(R.string.goal_available_members),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = "${goal.currentMembers}/${goal.maxMembers}명", textAlign = TextAlign.Center)
        }
    }
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
            state = GoalState.SOLD_OUT,
        )
        GoalItem(
            goal = dummyGoal,
            navigateToDetail = {},
            modifier = Modifier.background(Color.White),
        )
    }
}
