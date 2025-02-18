package cmc.goalmate.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage

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
    modifier: Modifier = Modifier,
    image: String = "",
    contentDescription: String? = null,
    shape: Shape = RectangleShape,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    SubcomposeAsyncImage(
        model = image,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier.clip(shape),
        error = {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.image_goal_default),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = modifier
            )
        },
    )
}

@Composable
@Preview
private fun GoalMateImagePreview() {
    GoalMateTheme {
        GoalMateImage(
            image = "https://picsum.photos/seed/11/156/214",
        )
    }
}
