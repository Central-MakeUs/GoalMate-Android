package cmc.goalmate.presentation.ui.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.comments.model.CommentRoomsUiModel
import cmc.goalmate.presentation.ui.mygoals.components.GoalStatusTag

@Composable
fun CommentRoomsContent(
    goalComments: List<CommentRoomsUiModel>,
    onAction: (CommentRoomsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(GoalMateDimens.VerticalArrangementSpaceLarge),
        modifier = modifier,
    ) {
        items(items = goalComments) { goalComment ->
            RoomItem(
                goal = goalComment,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        onAction(
                            CommentRoomsAction.SelectCommentRoom(roomId = goalComment.roomId),
                        )
                    }
                    .padding(horizontal = GoalMateDimens.HorizontalPadding, vertical = 4.dp),
            )
        }
    }
}

@Composable
private fun RoomItem(
    goal: CommentRoomsUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier,
    ) {
        GoalMateImage(
            image = goal.imageUrl,
            shape = CircleShape,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = goal.mentorName,
                    style = MaterialTheme.goalMateTypography.buttonLabelLarge,
                    color = MaterialTheme.goalMateColors.onBackground,
                )
                GoalStatusTag(daysFromStart = goal.remainingDays, goalState = goal.goalState)
            }
            Text(
                text = goal.title,
                style = MaterialTheme.goalMateTypography.bodySmall,
                color = MaterialTheme.goalMateColors.onBackground,
            )
        }

        Box(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically),
        ) {
            if (goal.hasNewComment) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.goalMateColors.onError)
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun GoalCommentsContentPreview() {
    GoalMateTheme {
        CommentRoomsContent(
            goalComments = listOf(CommentRoomsUiModel.DUMMY),
            onAction = {},
        )
    }
}
