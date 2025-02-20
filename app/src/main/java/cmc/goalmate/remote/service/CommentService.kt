package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.request.CommentRequest
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.CommentResponse
import cmc.goalmate.remote.dto.response.CommentRoomsResponse
import cmc.goalmate.remote.dto.response.CommentsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @GET("$COMMENT_ROOM/my")
    suspend fun getCommentRooms(): ApiResponse<BaseResponse<CommentRoomsResponse>>

    @GET("$COMMENT_ROOM/{roomId}/comments")
    suspend fun getComments(
        @Path("roomId") roomId: Int,
    ): ApiResponse<BaseResponse<CommentsResponse>>

    @POST("$COMMENT_ROOM/{roomId}/comments")
    suspend fun postComment(
        @Path("roomId") roomId: Int,
        @Body commentRequest: CommentRequest,
    ): ApiResponse<BaseResponse<CommentResponse>>

    @PATCH("$COMMENT_ROOM/{commentId}")
    suspend fun updateComment(
        @Path("commentId") commentId: Int,
        @Body commentUpdatedRequest: CommentRequest,
    ): ApiResponse<BaseResponse<CommentResponse>>

    @DELETE("$COMMENT_ROOM/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: Int,
    ): ApiResponse<BaseResponse<Unit>>

    companion object {
        private const val COMMENT_ROOM = "/comment-rooms"
    }
}
