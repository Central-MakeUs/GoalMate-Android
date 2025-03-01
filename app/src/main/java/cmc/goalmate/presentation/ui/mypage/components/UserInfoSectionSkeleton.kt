package cmc.goalmate.presentation.ui.mypage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.ui.progress.components.RoundedBoxSkeleton

@Composable
fun UserInfoSectionSkeleton(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(122.dp)
                .background(
                    color = MaterialTheme.goalMateColors.surface,
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(
                    vertical = GoalMateDimens.BoxContentVerticalPadding,
                    horizontal = GoalMateDimens.HorizontalPadding,
                ),
        )

        Spacer(Modifier.size(GoalMateDimens.VerticalSpacerLarge))

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(GoalMateDimens.VerticalArrangementSpaceSmall),
        ) {
            RoundedBoxSkeleton(width = 60.dp, height = 24.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(97.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        color = MaterialTheme.goalMateColors.surface,
                    )
                    .padding(vertical = GoalMateDimens.BoxContentVerticalPadding),
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun UserInfoSectionSkeletonPreview() {
    GoalMateTheme {
        UserInfoSectionSkeleton()
    }
}
