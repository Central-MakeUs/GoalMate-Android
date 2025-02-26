package cmc.goalmate.local.di

import android.content.Context
import cmc.goalmate.local.TokenDataStore
import cmc.goalmate.local.UserInfoDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context,
    ): TokenDataStore = TokenDataStore(context)

    @Provides
    @Singleton
    fun provideUserInfoDataStore(
        @ApplicationContext context: Context,
    ): UserInfoDataStore = UserInfoDataStore(context)
}
