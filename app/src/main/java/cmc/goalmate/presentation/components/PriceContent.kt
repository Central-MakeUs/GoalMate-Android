package cmc.goalmate.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun PriceContent(
    discount: String,
    price: String,
    totalPrice: String,
    size: PriceContentStyleSize,
    discountTextColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Text(text = discount, style = size.discountTextStyle(), color = discountTextColor)
            Spacer(modifier = Modifier.size(size.rowSpacing))
            Text(
                text = price,
                style = size.priceTextStyle().copy(
                    textDecoration = TextDecoration.LineThrough,
                ),
                color = MaterialTheme.goalMateColors.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.size(size.colSpacing))
        Text(text = totalPrice, style = size.totalPriceTextStyle())
    }
}

enum class PriceContentStyleSize(
    val rowSpacing: Dp,
    val colSpacing: Dp,
) {
    LARGE(rowSpacing = 8.dp, colSpacing = 6.dp),
    SMALL(rowSpacing = 4.dp, colSpacing = 4.dp),
}

@Composable
private fun PriceContentStyleSize.discountTextStyle(): TextStyle =
    when (this) {
        PriceContentStyleSize.SMALL -> MaterialTheme.goalMateTypography.buttonLabelMedium
        PriceContentStyleSize.LARGE -> MaterialTheme.goalMateTypography.buttonLabelSmall
    }

@Composable
private fun PriceContentStyleSize.priceTextStyle(): TextStyle =
    when (this) {
        PriceContentStyleSize.SMALL -> MaterialTheme.goalMateTypography.caption
        PriceContentStyleSize.LARGE -> MaterialTheme.goalMateTypography.buttonLabelSmall
    }

@Composable
private fun PriceContentStyleSize.totalPriceTextStyle(): TextStyle =
    when (this) {
        PriceContentStyleSize.SMALL -> MaterialTheme.goalMateTypography.body
        PriceContentStyleSize.LARGE -> MaterialTheme.goalMateTypography.subtitle
    }
