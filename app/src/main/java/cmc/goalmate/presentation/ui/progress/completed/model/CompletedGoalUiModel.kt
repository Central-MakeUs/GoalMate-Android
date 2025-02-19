package cmc.goalmate.presentation.ui.progress.completed.model

import cmc.goalmate.domain.model.MenteeGoalInfo
import cmc.goalmate.domain.model.MenteeGoalStatus
import cmc.goalmate.presentation.ui.util.calculateProgress
import java.time.format.DateTimeFormatter

data class CompletedGoalUiModel(
    val id: Int,
    val title: String,
    val mentor: String,
    val period: String,
    val startDate: String,
    val endDate: String,
    val achievementProgress: Float,
    val finalComment: String,
) {
    companion object {
        val DUMMY = CompletedGoalUiModel(
            id = 0,
            title = "마루와 함께하는 백엔드 서버 찐천재",
            mentor = "마루",
            period = "30일",
            startDate = "2025년 10월 19일",
            endDate = "2025년 11월 19일",
            achievementProgress = 80f,
            finalComment = "김골메이트님!\n" +
                "30일동안 고생 많았어요~! 어떠셨어요? 조금 힘들었죠? 하지만 지금 김골메이트님은 30일 전보다 훨씬 성장했답니다! 앞으로도 응원할게 요!김골메이트님!\n" +
                "30일동안 고생 많았어요~! 어떠셨어요? 조금 힘들었죠? 하지만 지금 김골메이트님은 30일 전보다 훨씬 성장했답니다! 앞으로도 응원할게 요!김골메이트님!\n" +
                "30일동안 고생 많았어요~! 어떠셨어요? 조금 힘들었죠? 하지만 지금 김골메이트님은 30일 전보다 훨씬 성장했답니다! 앞으로도 응원할게 요!김골메이트님!\n" +
                "30일동안 고생 많았어요~! 어떠셨어요? 조금 힘들었죠? 하지만 지금 김골메이트님은 30일 전보다 훨씬 성장했답니다! 앞으로도 응원할게 요!김골메이트님!\n" +
                "30일동안 고생 많았어요~! 어떠셨어요? 조금 힘들었죠? 하지만 지금 김골메이트님은 30일 전보다 훨씬 성장했답니다! 앞으로도 응원할게 요!김골메이트님!\n" +
                "30일동안 고생 많았어요~! 어떠셨어요? 조금 힘들었죠? 하지만 지금 김골메이트님은 30일 전보다 훨씬 성장했답니다! 앞\n" +
                "f",
        )
    }
}

internal fun MenteeGoalInfo.toUi(formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")): CompletedGoalUiModel {
    require(menteeGoalStatus is MenteeGoalStatus.Completed) { "Goal status must be Completed : $menteeGoalStatus" }
    return CompletedGoalUiModel(
        id = id,
        title = title,
        mentor = mentorName,
        period = "${period}일",
        startDate = "${startDate.format(formatter)} 부터",
        endDate = "${endDate.format(formatter)} 까지",
        achievementProgress = calculateProgress(totalCompletedCount, totalTodoCount),
        finalComment = menteeGoalStatus.finalComment,
    )
}
