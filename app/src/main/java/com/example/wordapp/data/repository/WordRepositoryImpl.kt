package com.example.wordapp.data.repository

import com.example.wordapp.data.network.WordApi
import com.example.wordapp.domain.model.Word
import com.example.wordapp.domain.model.WordData
import com.example.wordapp.domain.model.WordDataType
import com.example.wordapp.domain.repository.WordRepository
import com.example.wordapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WordRepositoryImpl(private val wordApi: WordApi) : WordRepository {
    override fun getWordDefinition(word: String): Flow<Resource<Word>> = flow {

        emit(Resource.Loading())

        try {
            val wordDefinitionDto = wordApi.getWordDefinitions(word)
            val definitions = wordDefinitionDto.definitions.map { WordData(it.definition,WordDataType.CONTENT) }
            val wordInfoList = mutableListOf<WordData>()
            wordInfoList.add(WordData("Definitions",WordDataType.HEADING))
            wordInfoList.addAll(definitions)
            emit(Resource.Success(Word(wordDefinitionDto.word,wordInfoList)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something Went Wrong"))
        }
    }
}