package cmc.goalmate.presentation.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors

@Composable
fun ImageSlider(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier,
        ) { page ->
//            GoalMateImage(
//                image = imageUrls[page],
//            )
            Box(
                modifier = modifier
                    .size(width = 360.dp, height = 270.dp)
                    .background(color = MaterialTheme.goalMateColors.primaryVariant),
                contentAlignment = Alignment.Center,
            ) {
                Text("이미지 : ${imageUrls[page]}", modifier = Modifier.align(Alignment.Center))
            }
        }

        Indicator(
            pagerState,
            modifier = Modifier
                .padding(bottom = 15.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun Indicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val indicatorColor = indicatorColor(
                pagerState.currentPage == iteration,
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(indicatorColor)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.goalMateColors.outline,
                        shape = CircleShape,
                    )
                    .size(8.dp),
            )
        }
    }
}

@Composable
private fun indicatorColor(isSelected: Boolean): Color =
    if (isSelected) {
        MaterialTheme.goalMateColors.onBackground
    } else {
        MaterialTheme.goalMateColors.thinDivider
    }

@Composable
@Preview
private fun ImageSliderPreview() {
    GoalMateTheme {
        ImageSlider(
            imageUrls = listOf("이미지1", "이미지2", "이미지3"),
            modifier = Modifier,
        )
    }
}
