package cmc.goalmate.remote.di

import cmc.goalmate.remote.service.AuthService
import cmc.goalmate.remote.service.GoalService
import cmc.goalmate.remote.service.MenteeGoalService
import cmc.goalmate.remote.service.MenteeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideMenteeService(retrofit: Retrofit): MenteeService = retrofit.create(MenteeService::class.java)

    @Provides
    @Singleton
    fun provideGoalService(retrofit: Retrofit): GoalService = retrofit.create(GoalService::class.java)

    @Provides
    @Singleton
    fun provideMenteeGoalService(retrofit: Retrofit): MenteeGoalService = retrofit.create(MenteeGoalService::class.java)
}
