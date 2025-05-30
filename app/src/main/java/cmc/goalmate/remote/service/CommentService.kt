package cmc.goalmate.remote.service

import cmc.goalmate.remote.dto.base.ApiResponse
import cmc.goalmate.remote.dto.request.CommentRequest
import cmc.goalmate.remote.dto.response.BaseResponse
import cmc.goalmate.remote.dto.response.CommentResponse
import cmc.goalmate.remote.dto.response.CommentRoomsResponse
import cmc.goalmate.remote.dto.response.CommentsResponse
import cmc.goalmate.remote.dto.response.NewCommentCountResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {
    @GET("$COMMENT_ROOM/my")
    suspend fun getCommentRooms(
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("size") size: Int = DEFAULT_SIZE,
    ): ApiResponse<BaseResponse<CommentRoomsResponse>>

    @GET("$COMMENT_ROOM/{roomId}/comments")
    suspend fun getComments(
        @Path("roomId") roomId: Int,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("size") size: Int = DEFAULT_SIZE,
    ): ApiResponse<BaseResponse<CommentsResponse>>

    @POST("$COMMENT_ROOM/{roomId}/comments")
    suspend fun postComment(
        @Path("roomId") roomId: Int,
        @Body commentRequest: CommentRequest,
    ): ApiResponse<BaseResponse<CommentResponse>>

    @PUT("$COMMENT_URL/{commentId}")
    suspend fun updateComment(
        @Path("commentId") commentId: Int,
        @Body commentUpdatedRequest: CommentRequest,
    ): ApiResponse<BaseResponse<CommentResponse>>

    @DELETE("$COMMENT_URL/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: Int,
    ): ApiResponse<BaseResponse<Unit>>

    @GET("$COMMENT_URL/new")
    suspend fun getNewCommentCount(): ApiResponse<BaseResponse<NewCommentCountResponse>>

    companion object {
        private const val COMMENT_ROOM = "/comment-rooms"
        private const val COMMENT_URL = "/comments"
        const val DEFAULT_PAGE = 1
        private const val DEFAULT_SIZE = 20
    }
}
