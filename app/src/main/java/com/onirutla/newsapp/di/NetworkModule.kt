package com.onirutla.newsapp.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.onirutla.newsapp.BuildConfig.NEWS_APP_API_KEY
import com.onirutla.newsapp.BuildConfig.NEWS_APP_BASE_URL
import com.onirutla.newsapp.core.source.remote.api_services.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val interceptor = Interceptor {
            val url = it.request()
                .url
                .newBuilder()
                .addQueryParameter("apiKey", NEWS_APP_API_KEY)
                .build()

            val request = it.request()
                .newBuilder()
                .addHeader("X-Api-Key", NEWS_APP_API_KEY)
                .addHeader("Authorization", NEWS_APP_API_KEY)
                .url(url)
                .build()

            it.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideKotlinSerialization(): Json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        isLenient = false
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        serialization: Json,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(serialization.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(EitherCallAdapterFactory.create())
        .baseUrl(NEWS_APP_BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)
}