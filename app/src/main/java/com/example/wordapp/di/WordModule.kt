package com.example.wordapp.di

import com.example.wordapp.data.network.WordApi
import com.example.wordapp.data.network.WordApi.Companion.BASE_URL
import com.example.wordapp.data.repository.WordRepositoryImpl
import com.example.wordapp.domain.repository.WordRepository
import com.example.wordapp.domain.usecase.GetWordDefinitions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordModule {

    @Provides
    @Singleton
    fun getWordDefinitionUseCase(wordRepository:WordRepository):GetWordDefinitions = GetWordDefinitions(wordRepository)

    @Provides
    @Singleton
    fun getWordRepository(wordApi: WordApi):WordRepository = WordRepositoryImpl(wordApi)

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient):WordApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(WordApi::class.java)



    @Provides
    @Singleton
    fun getOkHttp(headerInterceptor: Interceptor):OkHttpClient = OkHttpClient.Builder().run {
        addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        addInterceptor(headerInterceptor)
        return@run build()
    }

    @Provides
    @Singleton
    fun getHeaderInterceptor() = Interceptor{

        val requestBuilder = it.request().newBuilder().apply {
            addHeader("X-RapidAPI-Host","wordsapiv1.p.rapidapi.com")
            addHeader("X-RapidAPI-Key","ea409421bdmshb8aa4b78c4b538ap10b4fbjsn97fae8fe61e4")
        }

        it.proceed(requestBuilder.build())

    }



}