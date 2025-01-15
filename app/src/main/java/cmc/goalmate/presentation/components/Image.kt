package cmc.goalmate.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun GoalMateImage(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    AsyncImage(
        model = image,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Composable
fun GoalMateImage(
    image: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    AsyncImage(
        model = image,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}
