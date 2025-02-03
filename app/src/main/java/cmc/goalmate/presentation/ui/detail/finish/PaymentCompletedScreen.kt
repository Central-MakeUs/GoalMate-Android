package cmc.goalmate.presentation.ui.detail.finish

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.AppBarWithBackButton
import cmc.goalmate.presentation.components.ButtonSize
import cmc.goalmate.presentation.components.GoalMateButton
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.detail.components.GoalOverviewCard
import cmc.goalmate.presentation.ui.detail.finish.navigation.GoalSummary

@Composable
fun PaymentCompletedScreen(
    goal: GoalSummary,
    navigateToAchievingGoal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AppBarWithBackButton(
            onBackButtonClicked = {},
        )

        PaymentCompletedContent(
            title = goal.title,
            mentorName = goal.mentor,
            price = goal.price,
            totalPrice = goal.totalPrice,
            onStartButtonClicked = navigateToAchievingGoal,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun PaymentCompletedContent(
    title: String,
    mentorName: String,
    price: String,
    totalPrice: String,
    onStartButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = GoalMateDimens.HorizontalPadding)
            .padding(bottom = GoalMateDimens.BottomMargin),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.size(32.dp))
        Box {
            GoalMateImage(
                image = R.drawable.image_goal_start,
                contentDescription = stringResource(R.string.goal_detail_complete),
                modifier = Modifier
                    .size(
                        width = GoalMateDimens.ContentImageWidth,
                        height = GoalMateDimens.ContentImageHeight,
                    ),
            )

            Text(
                text = stringResource(R.string.goal_detail_complete),
                style = MaterialTheme.goalMateTypography.subtitleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 49.dp),
            )
        }
        Spacer(Modifier.size(16.dp))
        GoalOverviewCard(
            title = title,
            mentorName = mentorName,
            price = price,
            totalPrice = totalPrice,
        )
        Spacer(Modifier.weight(1f))
        GoalMateButton(
            content = stringResource(R.string.goal_detail_start_button),
            onClick = onStartButtonClicked,
            buttonSize = ButtonSize.LARGE,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.size(GoalMateDimens.BottomMargin))
    }
}

@Composable
@Preview(showBackground = true)
private fun PaymentCompletedScreenPreview() {
    GoalMateTheme {
        PaymentCompletedScreen(
            goal = GoalSummary("", "", "", ""),
            navigateToAchievingGoal = {},
            modifier = Modifier.background(Color.White),
        )
    }
}
