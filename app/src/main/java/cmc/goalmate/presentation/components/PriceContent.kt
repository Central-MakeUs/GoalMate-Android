package cmc.goalmate.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun PriceContent(
    discount: String,
    price: String,
    totalPrice: String,
    style: PriceContentStyle,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Text(text = discount, style = style.discountTextStyle)
            Spacer(modifier = Modifier.size(style.rowSpacing))
            Text(
                text = price,
                style = style.priceTextStyle.copy(
                    textDecoration = TextDecoration.LineThrough,
                ),
            )
        }
        Spacer(modifier = Modifier.size(style.colSpacing))
        Text(text = totalPrice, style = style.totalPriceTextStyle)
    }
}

data class PriceContentStyle(
    val discountTextStyle: TextStyle,
    val priceTextStyle: TextStyle,
    val totalPriceTextStyle: TextStyle,
    val rowSpacing: Dp,
    val colSpacing: Dp,
) {
    companion object {
        val Small = PriceContentStyle(
            discountTextStyle = goalMateTypography.buttonLabelMedium,
            priceTextStyle = goalMateTypography.caption,
            totalPriceTextStyle = goalMateTypography.body,
            rowSpacing = 4.dp,
            colSpacing = 4.dp,
        )
        val Large = PriceContentStyle(
            discountTextStyle = goalMateTypography.buttonLabelSmall,
            priceTextStyle = goalMateTypography.buttonLabelSmall,
            totalPriceTextStyle = goalMateTypography.subtitle,
            rowSpacing = 8.dp,
            colSpacing = 6.dp,
        )
    }
}
