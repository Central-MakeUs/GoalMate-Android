package cmc.goalmate.local.di

import cmc.goalmate.data.datasource.LocalUserDataSource
import cmc.goalmate.data.datasource.TokenDataSource
import cmc.goalmate.local.datasource.LocalUserDataSourceImpl
import cmc.goalmate.local.datasource.TokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindTokenDataSource(tokenDataSourceImpl: TokenDataSourceImpl): TokenDataSource

    @Binds
    @Singleton
    abstract fun bindLocalUserDataSource(localUserDataSourceImpl: LocalUserDataSourceImpl): LocalUserDataSource
}
