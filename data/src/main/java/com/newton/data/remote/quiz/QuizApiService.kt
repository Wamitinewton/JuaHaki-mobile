package com.newton.data.remote.quiz

import com.newton.data.dto.quiz.*
import com.newton.data.remote.ApiConstants
import com.newton.data.remote.ApiResponse
import retrofit2.http.*

interface QuizApiService {
    @GET(ApiConstants.GET_TODAY_QUIZ)
    suspend fun getTodaysQuiz(): ApiResponse<CivicQuizInfoResponse>

    @GET(ApiConstants.GET_QUIZ_INFO)
    suspend fun getQuizInfo(
        @Query("date") date: String,
    ): ApiResponse<CivicQuizInfoResponse>

    @POST(ApiConstants.START_QUIZ)
    suspend fun startQuiz(): ApiResponse<StartCivicQuizResponse>

    @POST(ApiConstants.SUBMIT_ANSWER)
    suspend fun submitAnswer(
        @Body request: SubmitCivicAnswerRequest,
    ): ApiResponse<SubmitCivicAnswerResponse>

    @GET(ApiConstants.GET_SESSION_STATUS)
    suspend fun getSessionStatus(
        @Path("sessionId") sessionId: String,
    ): ApiResponse<CivicQuizSessionResponse>

    @POST(ApiConstants.ABANDON_SESSION)
    suspend fun abandonSession(
        @Path("sessionId") sessionId: String,
    ): ApiResponse<Unit>

    @GET(ApiConstants.GET_QUIZ_RESULTS)
    suspend fun getQuizResults(
        @Path("sessionId") sessionId: String,
    ): ApiResponse<UserQuizSummaryDto>

    @GET(ApiConstants.GET_QUIZ_HISTORY_METADATA)
    suspend fun getUserQuizHistoryMetadata(): ApiResponse<List<UserQuizMetadataDto>>

    @GET(ApiConstants.GET_QUIZ_HISTORY_DETAILS)
    suspend fun getQuizDetailsBySessionId(
        @Path("sessionId") sessionId: String,
    ): ApiResponse<UserQuizSummaryDto>

    @GET(ApiConstants.GET_TODAY_LEADERBOARD)
    suspend fun getTodaysLeaderboard(): ApiResponse<QuizLeaderboardResponse>

    @GET(ApiConstants.GET_LEADERBOARD)
    suspend fun getLeaderboard(
        @Query("date") date: String,
    ): ApiResponse<QuizLeaderboardResponse>

    @GET(ApiConstants.GET_QUIZ_STATISTICS)
    suspend fun getQuizStatistics(
        @Query("date") date: String,
    ): ApiResponse<QuizStatisticsDto>
}
