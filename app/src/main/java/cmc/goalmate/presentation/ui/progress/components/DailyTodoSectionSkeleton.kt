package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun DailyTodoSectionSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(
            horizontal = GoalMateDimens.HorizontalPadding,
            vertical = GoalMateDimens.ItemVerticalPaddingLarge,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundedBoxSkeleton(width = 95.dp, height = 19.dp)

            RoundedBoxSkeleton(width = 136.dp, height = 30.dp)
        }

        Spacer(Modifier.size(GoalMateDimens.ItemVerticalPaddingLarge))

        Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
            repeat(3) {
                TodoSkeleton()
            }
        }

        Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))

        RoundedBoxSkeleton(width = 73.dp, height = 19.dp)

        Spacer(Modifier.size(16.dp))

        RoundedBoxSkeleton(width = 200.dp, height = 100.dp, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(Modifier.size(12.dp))

        Box(
            modifier = Modifier.fillMaxWidth().height(
                45.dp,
            ).clip(RoundedCornerShape(20.dp)).background(MaterialTheme.goalMateColors.pending),
        )
    }
}

@Composable
private fun TodoSkeleton() {
    Row {
        Box(
            modifier = Modifier.size(18.dp).clip(RoundedCornerShape(4.dp))
                .background(color = MaterialTheme.goalMateColors.outline),
        )
        Spacer(Modifier.size(10.dp))
        RoundedBoxSkeleton(width = 286.dp, height = 20.dp, modifier = Modifier.weight(1f))
    }
}

@Composable
fun RoundedBoxSkeleton(
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(width = width, height = height)
            .background(
                color = MaterialTheme.goalMateColors.pending,
                shape = RoundedCornerShape(20.dp),
            ),
    )
}

@Composable
@Preview
private fun SkeletonPreview() {
    GoalMateTheme {
        DailyTodoSectionSkeleton()
    }
}
