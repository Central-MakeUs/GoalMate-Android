package cmc.goalmate.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
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
    shape: Shape = RectangleShape,
) {
    AsyncImage(
        model = image,
        contentDescription = contentDescription,
        modifier = modifier.clip(shape),
    )
}
