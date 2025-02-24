package cmc.goalmate.presentation.ui.mypage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun ProfileHeaderSection(
    title: String,
    subtitle: String,
    targetIcon: Int,
    iconBackground: Color,
    onIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.primary,
                shape = RoundedCornerShape(24.dp),
            )
            .padding(
                vertical = GoalMateDimens.BoxContentVerticalPadding,
                horizontal = GoalMateDimens.HorizontalPadding,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.goalMateTypography.subtitleSmall,
                color = MaterialTheme.goalMateColors.onBackground,
            )
            Icon(
                imageVector = ImageVector.vectorResource(targetIcon),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = iconBackground)
                    .clickable { onIconClicked() }
                    .padding(5.dp),
                tint = MaterialTheme.goalMateColors.onBackground,
            )
        }
        Spacer(Modifier.size(GoalMateDimens.VerticalArrangementSpaceSmall))
        Text(
            text = subtitle,
            style = MaterialTheme.goalMateTypography.body,
            color = MaterialTheme.goalMateColors.onBackground,
        )
    }
}
