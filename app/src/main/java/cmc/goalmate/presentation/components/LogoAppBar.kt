package cmc.goalmate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import cmc.goalmate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoAppBar() {
    TopAppBar(
        title = {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.icon_logo_sub),
                contentDescription = stringResource(R.string.app_name),
            )
        },
    )
}
