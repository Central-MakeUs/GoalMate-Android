package cmc.goalmate.presentation.ui.progress.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmc.goalmate.R
import cmc.goalmate.presentation.components.GoalMateCheckBox
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.White
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography
import cmc.goalmate.presentation.ui.progress.inprogress.model.TodoGoalUiModel
import cmc.goalmate.presentation.ui.util.singleClickable

@Composable
fun ToDoItem(
    todo: TodoGoalUiModel,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isTipVisible by remember { mutableStateOf<Boolean>(false) }
    val hapticFeedback = LocalHapticFeedback.current
    Row(
        modifier = modifier,
    ) {
        GoalMateCheckBox(
            isChecked = todo.isCompleted,
            modifier = Modifier
                .size(44.dp)
                .padding(13.dp)
                .singleClickable(noRipple = true) {
                    onCheckedChange()
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                },
        )
        Column(
            modifier = Modifier.weight(1f).padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = todo.content,
                style = MaterialTheme.goalMateTypography.body.copy(fontSize = 17.sp),
                color = MaterialTheme.goalMateColors.onBackground,
            )

            TodoDetail(
                time = todo.time,
                hasTip = !todo.tip.isNullOrBlank(),
                toggleTip = { isTipVisible = !isTipVisible },
            )

            if (isTipVisible && todo.tip != null) {
                TipContent(tip = todo.tip, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun TodoDetail(
    time: String,
    hasTip: Boolean,
    toggleTip: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = time,
            style = MaterialTheme.goalMateTypography.body,
            color = MaterialTheme.goalMateColors.labelTitle,
        )

        if (hasTip) {
            Text(
                text = "|",
                style = MaterialTheme.goalMateTypography.body,
                color = MaterialTheme.goalMateColors.outline,
            )

            TipToggleButton(onClicked = toggleTip)
        }
    }
}

@Composable
private fun TipToggleButton(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var rotated by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
            .clip(
                RoundedCornerShape(6.dp),
            )
            .background(MaterialTheme.goalMateColors.surface)
            .clickable {
                onClicked()
                rotated = !rotated
            }
            .padding(vertical = 4.dp, horizontal = 9.dp),
    ) {
        Text(
            text = "멘토TIP",
            style = MaterialTheme.goalMateTypography.captionSemiBold,
            color = MaterialTheme.goalMateColors.onSurface,
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_triangle),
            contentDescription = null,
            tint = MaterialTheme.goalMateColors.onSurface,
            modifier = Modifier.graphicsLayer(
                rotationZ = if (rotated) 180f else 0f,
            ),
        )
    }
}

@Composable
private fun TipContent(
    tip: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = tip,
        style = MaterialTheme.goalMateTypography.bodySmallMedium,
        color = MaterialTheme.goalMateColors.onSurface,
        modifier = modifier
            .background(
                color = MaterialTheme.goalMateColors.background,
                shape = RoundedCornerShape(14.dp),
            )
            .border(
                1.dp,
                color = MaterialTheme.goalMateColors.thinDivider,
                RoundedCornerShape(14.dp),
            )
            .padding(12.dp),
    )
}

@Composable
@Preview(showBackground = true)
private fun ToDoItemPreview() {
    GoalMateTheme {
        ToDoItem(
            todo = TodoGoalUiModel(
                0,
                "팝송 부르기팝송 부르기팝송 부르기팝송 부르기팝송 부르기팝송 부르기팝송 부르기 팝송 부르기",
                "30분",
                true,
                tip = "영어 단어 보카 암기 할 때는 이렇게 하는 게 좋아요 블라블라",
            ),
            onCheckedChange = {},
            modifier = Modifier.background(White),
        )
    }
}
