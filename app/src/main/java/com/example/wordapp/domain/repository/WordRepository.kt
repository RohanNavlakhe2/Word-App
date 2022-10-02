package com.example.wordapp.domain.repository

import com.example.wordapp.domain.model.Word
import com.example.wordapp.domain.model.WordData
import com.example.wordapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun getWordDefinition(word: String): Flow<Resource<Word>>
}