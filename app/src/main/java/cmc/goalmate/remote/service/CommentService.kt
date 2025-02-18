package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.CommentRoomsResponse
import retrofit2.http.GET

interface CommentService {
    @GET("$COMMENT_ROOM/my")
    suspend fun getCommentRooms(): ApiResponse<BaseResponse<CommentRoomsResponse>>

    companion object {
        private const val COMMENT_ROOM = "/comment-rooms"
    }
}
