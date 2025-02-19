package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.CommentRoomsResponse
import cmc.goalmate.remote.dto.response.CommentsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentService {
    @GET("$COMMENT_ROOM/my")
    suspend fun getCommentRooms(): ApiResponse<BaseResponse<CommentRoomsResponse>>

    @GET("$COMMENT_ROOM/{roomId}/comments")
    suspend fun getComments(
        @Path("roomId") roomId: Int,
    ): ApiResponse<BaseResponse<CommentsResponse>>

    companion object {
        private const val COMMENT_ROOM = "/comment-rooms"
    }
}
