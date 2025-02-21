package cmc.goalmate.remote.di
import cmc.goalmate.BuildConfig
import cmc.goalmate.local.TokenDataStore
import cmc.goalmate.remote.adapter.GoalMateCallAdapterFactory
import cmc.goalmate.remote.interceptor.AuthAuthenticator
import cmc.goalmate.remote.interceptor.AuthorizationInterceptor
import cmc.goalmate.remote.interceptor.LoginInterceptor
import cmc.goalmate.remote.service.AuthService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }

    @Provides
    @Singleton
    fun provideConverterFactory(json: Json): Converter.Factory = json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideGoalMateCallAdapterFactory(): GoalMateCallAdapterFactory = GoalMateCallAdapterFactory()

    @Provides
    @Singleton
    fun provideLoginInterceptor(): LoginInterceptor = LoginInterceptor()

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenDataStore: TokenDataStore): AuthorizationInterceptor = AuthorizationInterceptor(tokenDataStore)

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        authorizationInterceptor: AuthorizationInterceptor,
        loginInterceptor: LoginInterceptor,
        authenticator: AuthAuthenticator,
        callAdapterFactory: GoalMateCallAdapterFactory,
        converterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit
            .Builder()
            .client(
                createOkHttpClient {
                    authenticator(authenticator)
                    addInterceptor(loggingInterceptor)
                    addInterceptor(loginInterceptor)
                    addInterceptor(authorizationInterceptor)
                },
            )
            .baseUrl(baseUrl)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideAuthService(
        baseUrl: String,
        authorizationInterceptor: AuthorizationInterceptor,
        loginInterceptor: LoginInterceptor,
        callAdapterFactory: GoalMateCallAdapterFactory,
        converterFactory: Converter.Factory,
    ): AuthService =
        Retrofit
            .Builder()
            .client(
                createOkHttpClient {
                    addInterceptor(loggingInterceptor)
                    addInterceptor(loginInterceptor)
                    addInterceptor(authorizationInterceptor)
                },
            )
            .baseUrl(baseUrl)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .build()
            .create(AuthService::class.java)

    private fun createOkHttpClient(interceptors: OkHttpClient.Builder.() -> Unit = { }): OkHttpClient =
        OkHttpClient
            .Builder()
            .callTimeout(Duration.ofMinutes(1))
            .apply(interceptors)
            .build()
}

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
