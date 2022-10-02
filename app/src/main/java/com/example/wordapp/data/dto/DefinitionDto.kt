package com.example.wordapp.data.dto

data class DefinitionDto(
    val definition: String,
    val partOfSpeech: String
){
    fun toDefinition() = definition
}