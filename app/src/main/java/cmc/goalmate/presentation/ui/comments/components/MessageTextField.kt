package cmc.goalmate.presentation.ui.comments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.color.Grey400
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

// .heightIn(max = 200.dp).verticalScroll(scrollState)

@Composable
fun MessageTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation = VisualTransformation.None,
        textStyle = MaterialTheme.goalMateTypography.body.merge(TextStyle(color = MaterialTheme.goalMateColors.onBackground)),
        modifier = modifier.verticalScroll(scrollState),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = modifier
                    .heightIn(max = 216.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(30.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.goalMateColors.outline,
                        shape = RoundedCornerShape(30.dp),
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.comments_detail_placeholder),
                        color = MaterialTheme.goalMateColors.labelTitle,
                        style = MaterialTheme.goalMateTypography.body,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
@Preview
fun MessageTextFieldPreview() {
    var inputs by remember { mutableStateOf("안녕하세요\n안녕하세요\n안녕하세요\n안녕하세요\n안녕하세요\n반갑습니다.안녕하세요\n언제까지 내려가는거예요\n아직인것같죠\n한번더!!") }
    GoalMateTheme {
        MessageTextField(
            value = inputs,
            onValueChange = { inputs = it },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
