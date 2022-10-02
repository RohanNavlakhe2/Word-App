package com.example.wordapp.domain.usecase

import com.example.wordapp.domain.model.Word
import com.example.wordapp.domain.model.WordData
import com.example.wordapp.domain.repository.WordRepository
import com.example.wordapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWordDefinitions(private val wordRepository: WordRepository) {
    operator fun invoke(word:String): Flow<Resource<Word>> {
        return wordRepository.getWordDefinition(word)


        /* return wordRepository.getWordDefinition(word).map {
            if (it is Resource.Success)
                return@map Resource.Success(it.data?.copy(wordDataList = it.data.wordDataList.subList(0,3)))
            else
                return@map it
        }*/
    }
}