package cmc.goalmate.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
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
            .background(MaterialTheme.goalMateColors.background)
            .padding(horizontal = GoalMateDimens.HorizontalPadding).padding(top = 20.dp, bottom = 14.dp),
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
    title: String? = null,
    @DrawableRes iconRes: Int = R.drawable.icon_back,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(GoalMateDimens.TopBarHeight)
            .background(MaterialTheme.goalMateColors.background)
            .padding(horizontal = 4.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(12.dp)
                .clickable {
                    onBackButtonClicked()
                },
        )
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.goalMateTypography.subtitle,
                color = MaterialTheme.goalMateColors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(horizontal = 60.dp),
            )
        }
    }
}

@Composable
fun HeaderTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.height(GoalMateDimens.TopBarHeight),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = MaterialTheme.goalMateTypography.subtitle,
            modifier = Modifier.padding(
                horizontal = GoalMateDimens.HorizontalPadding,
                vertical = 14.dp,
            ),
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
            AppBarWithBackButton(onBackButtonClicked = {}, title = "다온과 함께하는 영어 완전 정복 목표 뿌시기")
        }
    }
}
