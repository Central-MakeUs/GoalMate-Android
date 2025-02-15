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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmc.goalmate.R
import cmc.goalmate.presentation.theme.GoalMateDimens
import cmc.goalmate.presentation.theme.GoalMateTheme
import cmc.goalmate.presentation.theme.goalMateColors
import cmc.goalmate.presentation.theme.goalMateTypography

@Composable
private fun ProfileHeaderSectionBackGround(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
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
            icon()
        }
        Spacer(Modifier.size(GoalMateDimens.VerticalArrangementSpaceSmall))
        Text(
            text = subtitle,
            style = MaterialTheme.goalMateTypography.body,
            color = MaterialTheme.goalMateColors.onBackground,
        )
    }
}

@Composable
fun NickNameSection(
    nickName: String,
    onEditButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ProfileHeaderSectionBackGround(
        title = "$nickName 님",
        subtitle = "안녕하세요!\n" + "골메이트에 오신 것을 환영해요",
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_edit),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.goalMateColors.completed,
                    )
                    .clickable { onEditButtonClicked() }
                    .padding(5.dp),
            )
        },
        modifier = modifier,
    )
}

@Composable
fun LoginSection(
    onLoginButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ProfileHeaderSectionBackGround(
        title = "로그인 회원가입",
        subtitle = "회원가입하고 무료 목표 참여권 받으세요.\n",
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_forward),
                contentDescription = null,
                tint = MaterialTheme.goalMateColors.onBackground,
                modifier = Modifier.clickable { onLoginButtonClicked() },
            )
        },
        modifier = modifier,
    )
}

@Composable
@Preview
private fun ProfileHeaderSectionPreview() {
    GoalMateTheme {
        LoginSection(
            onLoginButtonClicked = {},
        )
    }
}
