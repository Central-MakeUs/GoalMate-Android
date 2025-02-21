package cmc.goalmate.presentation.ui.detail

import cmc.goalmate.domain.model.GoalDetail
import cmc.goalmate.domain.model.MidObjective
import cmc.goalmate.domain.model.WeeklyObjective
import cmc.goalmate.presentation.ui.detail.navigation.GoalSummary
import cmc.goalmate.presentation.ui.home.GoalUiStatus
import cmc.goalmate.presentation.ui.home.toUi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class GoalDetailUiModel(
    val title: String,
    val mentorName: String,
    val imageUrls: List<String>,
    val isClosingSoon: Boolean,
    val category: String,
    val totalDates: String,
    val startDate: String,
    val endDate: String,
    val time: String,
    val price: String = TEMP_PRICE,
    val discount: String = TEMP_PRICE,
    val totalPrice: String = TEMP_PRICE,
    val currentMembers: Int,
    val maxMembers: Int,
    val state: GoalUiStatus,
    val description: String,
    val weeklyGoal: List<MilestoneUiModel>,
    val milestones: List<MilestoneUiModel>,
    val detailImageUrls: List<String>,
    val isParticipated: Boolean,
) {
    val isAvailable: Boolean
        get() = this.state == GoalUiStatus.AVAILABLE

    val remainingCount: Int
        get() = this.maxMembers - this.currentMembers

    companion object {
        private const val TEMP_PRICE = "0"
        val DUMMY = GoalDetailUiModel(
            title = "다온과 함께하는 영어 완전 정복 30일 목표",
            mentorName = "다온",
            imageUrls = listOf("image1", "image2"),
            category = "영어",
            isClosingSoon = false,
            totalDates = "30일",
            startDate = "2025년 01월 01일",
            endDate = "2025년 01월 30일",
            time = "하루 평균 1시간",
            price = "10,000원",
            discount = "100%",
            totalPrice = "0원",
            currentMembers = 7,
            maxMembers = 23,
            state = GoalUiStatus.AVAILABLE,
            description = "“영어를 하고 싶었지만 어떤 방법으로 해야 할 지, 루틴을 세우지만 어떤 방법이 효율적일지 고민이 많지 않았나요?”",
            weeklyGoal = listOf(MilestoneUiModel("1주차", "간단한 단어부터 시작하기"), MilestoneUiModel("2주차", "기본 문장 읽기")),
            milestones = listOf(MilestoneUiModel("1", "간단한 단어부터 시작하기"), MilestoneUiModel("2", "기본 문장 읽기")),
            detailImageUrls = listOf(""),
            isParticipated = false,
        )
    }
}

fun GoalDetailUiModel.toSummary(): GoalSummary =
    GoalSummary(
        title = title,
        mentor = mentorName,
        price = price,
        totalPrice = totalPrice,
    )

data class MilestoneUiModel(val label: String, val content: String)

fun GoalDetail.toUi(dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")): GoalDetailUiModel {
    val today = LocalDate.now()
    val endDate = today.plusDays(this.period.toLong())

    return GoalDetailUiModel(
        title = this.title,
        mentorName = this.mentorName,
        imageUrls = this.thumbnailImages,
        category = this.topic,
        totalDates = "${this.period}일",
        isClosingSoon = this.isClosingSoon,
        startDate = today.format(dateFormatter),
        endDate = endDate.format(dateFormatter),
        time = "하루 평균 ${this.dailyDuration}시간",
        currentMembers = this.currentParticipants,
        maxMembers = this.participantsLimit,
        state = this.goalStatus.toUi(),
        description = this.description,
        weeklyGoal = this.weeklyObjectives.map { it.toUi() },
        milestones = this.midObjectives.map { it.toUi() },
        detailImageUrls = this.contentImages,
        isParticipated = this.isParticipated,
    )
}

fun WeeklyObjective.toUi(): MilestoneUiModel =
    MilestoneUiModel(
        label = "${this.weekNumber}주",
        content = this.description,
    )

fun MidObjective.toUi(): MilestoneUiModel =
    MilestoneUiModel(
        label = "${this.sequence}",
        content = this.description,
    )
