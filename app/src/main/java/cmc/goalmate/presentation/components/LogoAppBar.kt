package cmc.goalmate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
fun LogoAppBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(GoalMateDimens.TopBarHeight)
            .background(MaterialTheme.goalMateColors.background)
            .padding(horizontal = GoalMateDimens.HorizontalPadding),
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.icon_logo_sub),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.align(alignment = Alignment.CenterStart),
        )
    }
}

@Composable
fun AppBarWithBackButton(
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(GoalMateDimens.TopBarHeight)
            .background(MaterialTheme.goalMateColors.background)
            .padding(horizontal = 4.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_back),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(12.dp)
                .clickable {
                    onBackButtonClicked()
                },
        )
        Text(
            text = "목표",
            style = MaterialTheme.goalMateTypography.subtitle,
            modifier = Modifier.align(alignment = Alignment.Center),
        )
    }
}

@Composable
@Preview
private fun LogoAppBarPreview() {
    GoalMateTheme {
        Column {
            LogoAppBar()
            Spacer(Modifier.size(12.dp))
            AppBarWithBackButton(onBackButtonClicked = {})
        }
    }
}
