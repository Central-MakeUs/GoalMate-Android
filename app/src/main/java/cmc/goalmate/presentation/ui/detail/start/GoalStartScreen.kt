package cmc.goalmate.presentation.ui.detail.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.detail.components.GoalOverviewCard
import cmc.goalmate.presentation.ui.detail.navigation.GoalSummary

@Composable
fun GoalStartScreen(
    goal: GoalSummary,
    onStartButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = GoalMateDimens.HorizontalPadding,
                vertical = GoalMateDimens.BottomMargin,
            ),
    ) {
        GoalOverviewCard(goal.title, goal.mentor)
        Spacer(Modifier.size(138.dp))
        GoalMateButton(
            content = stringResource(R.string.goal_detail_start_button),
            onClick = onStartButtonClicked,
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun InfoMessage(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.thickDivider,
                shape = RoundedCornerShape(20.dp),
            )
            .padding(GoalMateDimens.HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GoalMateImage(
            image = R.drawable.icon_warn_circle,
            modifier = Modifier.size(20.dp),
        )
        Spacer(Modifier.size(GoalMateDimens.HorizontalPadding))
        Column {
            Text(
                text = stringResource(R.string.goal_detail_start_warn_1),
                style = MaterialTheme.goalMateTypography.bodySmall,
                color = MaterialTheme.goalMateColors.onSurfaceVariant,
            )
            Spacer(Modifier.size(6.dp))
            Text(
                text = stringResource(R.string.goal_detail_start_warn_2),
                style = MaterialTheme.goalMateTypography.buttonLabelLarge,
                color = MaterialTheme.goalMateColors.onBackground,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun GoalStartScreenPreview() {
    GoalMateTheme {
        GoalStartScreen(
            goal = GoalSummary(
                title = "목표명",
                mentor = "멘토명",
                price = "10,000원",
                totalPrice = "0원",
            ),
            onStartButtonClicked = {},
            modifier = Modifier.background(White),
        )
    }
}
