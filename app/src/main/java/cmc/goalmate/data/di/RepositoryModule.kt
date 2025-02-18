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

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindGoalsRepository(goalsRepositoryImpl: GoalsRepositoryImpl): GoalsRepository

    @Binds
    abstract fun bindMenteeGoalRepository(menteeGoalRepositoryImpl: MenteeGoalRepositoryImpl): MenteeGoalRepository

    @Binds
    abstract fun bindCommentRepository(commentRepositoryImpl: CommentRepositoryImpl): CommentRepository
}
