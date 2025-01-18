package cmc.goalmate.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Grey600
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun GoalDateRange(
    startDate: String,
    endDate: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.thickDivider,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(12.dp),
    ) {
        GoalMateImage(image = icon, modifier = Modifier.size(24.dp))
        Spacer(Modifier.size(10.dp))
        Column {
            Text(
                text = startDate,
                style = MaterialTheme.goalMateTypography.bodySmallMedium,
                color = Grey600,
            )
            Text(
                text = endDate,
                style = MaterialTheme.goalMateTypography.bodySmallMedium,
                color = Grey600,
            )
        }
    }
}

@Preview
@Composable
private fun GoalDateRangePreview() {
    GoalMateTheme {
        GoalDateRange(
            startDate = "2025년 01월 01일 부터",
            endDate = "2025년 01월 30일 까지",
            icon = R.drawable.icon_calendar,
        )
    }
}
