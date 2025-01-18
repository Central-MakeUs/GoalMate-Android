package cmc.goalmate.presentation.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun GoalOverviewCard(
    title: String,
    mentorName: String,
    price: String,
    totalPrice: String,
    modifier: Modifier = Modifier,
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
    ) {
        InfoRow(
            title = stringResource(R.string.goal_detail_start_title),
            content = title,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        InfoRow(
            title = stringResource(R.string.goal_detail_start_mentor_title),
            content = mentorName,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        val priceText = buildAnnotatedString {
            withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                append(price)
            }
            append(" $totalPrice")
        }
        InfoRow(
            title = stringResource(R.string.goal_detail_start_price_title),
            content = totalPrice,
            annotatedContent = priceText,
        )
    }
}
