package com.example.wordapp.data.network

import com.example.wordapp.data.dto.WordDefinitionDto
import retrofit2.http.GET
import retrofit2.http.Path

interface WordApi {

    @GET("{word}/definitions")
    suspend fun getWordDefinitions(@Path("word") word:String):WordDefinitionDto

    companion object{
        const val BASE_URL = "https://wordsapiv1.p.rapidapi.com/words/"
    }
}