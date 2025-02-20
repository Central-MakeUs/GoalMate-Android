package cmc.goalmate.data.di

import cmc.goalmate.data.repository.AuthRepositoryImpl
import cmc.goalmate.data.repository.CommentRepositoryImpl
import cmc.goalmate.data.repository.GoalsRepositoryImpl
import cmc.goalmate.data.repository.MenteeGoalRepositoryImpl
import cmc.goalmate.data.repository.UserRepositoryImpl
import cmc.goalmate.domain.repository.AuthRepository
import cmc.goalmate.domain.repository.CommentRepository
import cmc.goalmate.domain.repository.GoalsRepository
import cmc.goalmate.domain.repository.MenteeGoalRepository
import cmc.goalmate.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindGoalsRepository(goalsRepositoryImpl: GoalsRepositoryImpl): GoalsRepository

    @Singleton
    @Binds
    abstract fun bindMenteeGoalRepository(menteeGoalRepositoryImpl: MenteeGoalRepositoryImpl): MenteeGoalRepository

    @Singleton
    @Binds
    abstract fun bindCommentRepository(commentRepositoryImpl: CommentRepositoryImpl): CommentRepository
}
