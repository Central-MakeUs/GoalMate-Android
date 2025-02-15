package cmc.goalmate.presentation.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateImage
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun PaymentCompleteTag(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.background,
                shape = RoundedCornerShape(20.dp),
            )
            .border(width = 1.dp, color = MaterialTheme.goalMateColors.outline, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = GoalMateDimens.HorizontalPadding, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GoalMateImage(
            image = R.drawable.icon_check_success,
            modifier = Modifier.size(12.dp),
        )
        Spacer(Modifier.size(4.dp))
        Text(
            text = stringResource(R.string.goal_detail_success_payment),
            style = MaterialTheme.goalMateTypography.caption,
            color = MaterialTheme.goalMateColors.onBackground,
        )
    }
}

@Composable
@Preview
fun PaymentCompleteTagPreview() {
    GoalMateTheme {
        PaymentCompleteTag()
    }
}

@Composable
fun FreeEntryCountTag(
    availableSeatCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.background,
                shape = RoundedCornerShape(24.dp),
            )
            .border(width = 1.dp, color = MaterialTheme.goalMateColors.onBackground, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = GoalMateDimens.HorizontalPadding, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_bell),
            contentDescription = null,
            tint =MaterialTheme.goalMateColors.onBackground
        )
        Spacer(Modifier.size(7.dp))
        Text(
            text = stringResource(R.string.goal_detail_available_seats, availableSeatCount),
            style = MaterialTheme.goalMateTypography.caption,
            color = MaterialTheme.goalMateColors.onBackground,
        )
    }
}

@Composable
@Preview
fun FreeEntryCountTagPreview() {
    GoalMateTheme {
        FreeEntryCountTag(availableSeatCount = 7)
    }
}
