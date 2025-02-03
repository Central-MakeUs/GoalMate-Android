package cmc.goalmate.presentation.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun GoalOverviewCard(
    title: String,
    mentorName: String,
    modifier: Modifier = Modifier,
    price: String = "",
    totalPrice: String ="",
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.background,
                shape = RoundedCornerShape(20.dp),
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.goalMateColors.outline,
                shape = RoundedCornerShape(20.dp),
            )
            .padding(
                vertical = GoalMateDimens.BottomMargin,
                horizontal = GoalMateDimens.HorizontalPadding,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        InfoRow(
            title = stringResource(R.string.goal_detail_start_title),
            content = title,
        )
        InfoRow(
            title = stringResource(R.string.goal_detail_start_mentor_title),
            content = mentorName,
        )
    }
}

@Composable
@Preview
private fun GoalOverviewCardPreview() {
    GoalMateTheme {
        GoalOverviewCard(
            title = "ANNA와 함께하는 영어 완전 정복 30일 목표",
            mentorName = "ANNA",
        )
    }
}
