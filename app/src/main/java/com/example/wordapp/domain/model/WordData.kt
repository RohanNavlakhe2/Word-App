package com.example.wordapp.domain.model

enum class WordDataType { HEADING, CONTENT}

data class WordData(val data:String,val type:WordDataType)
