package cmc.goalmate.data.mapper

import cmc.goalmate.data.model.CommentRoomDto
import cmc.goalmate.data.model.CommentRoomsDto
import cmc.goalmate.domain.model.CommentRoom
import cmc.goalmate.domain.model.CommentRooms
import cmc.goalmate.remote.dto.response.CommentRoomResponse
import cmc.goalmate.remote.dto.response.CommentRoomsResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun CommentRoomResponse.toData(): CommentRoomDto =
    CommentRoomDto(
        commentRoomId = commentRoomId,
        menteeGoalId = menteeGoalId,
        menteeGoalTitle = menteeGoalTitle,
        startDate = startDate,
        endDate = endDate,
        mentorName = mentorName,
        newCommentsCount = newCommentsCount,
        mentorProfileImage = mentorProfileImage,
    )

fun CommentRoomsResponse.toData(): CommentRoomsDto = CommentRoomsDto(rooms = this.commentRooms.map { it.toData() })

fun CommentRoomsDto.toDomain(): CommentRooms = CommentRooms(this.rooms.map { it.toDomain() })

fun CommentRoomDto.toDomain(formatter: DateTimeFormatter = goalMateDateFormatter): CommentRoom =
    CommentRoom(
        commentRoomId = commentRoomId,
        menteeGoalId = menteeGoalId,
        menteeGoalTitle = menteeGoalTitle,
        startDate = LocalDate.parse(startDate, formatter),
        endDate = LocalDate.parse(endDate, formatter),
        mentorName = mentorName,
        newCommentsCount = newCommentsCount,
        mentorProfileImage = mentorProfileImage,
    )
