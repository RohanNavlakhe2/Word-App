package com.example.wordapp.presentation.search_word

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordapp.domain.model.Word
import com.example.wordapp.domain.model.WordData
import com.example.wordapp.domain.usecase.GetWordDefinitions
import com.example.wordapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchWordViewModel @Inject constructor(private val getWordDefinitions: GetWordDefinitions): ViewModel() {

    private val _wordDefinitionsFlow  = MutableStateFlow<Resource<Word>?>(null)
    val wordDefinitionsFlow:StateFlow<Resource<Word>?> = _wordDefinitionsFlow.stateIn(viewModelScope,
        SharingStarted.Eagerly,null)

    private var data:Resource<Word> = Resource.Loading()
   /* private val _wordDefinitionLiveData = MutableLiveData<Resource<Word>>(data)
    val wordDefinitionLiveData = _wordDefinitionLiveData*/

    fun getDefinitions(word:String) = viewModelScope.launch {
        getWordDefinitions(word).onEach {
            data = it
           _wordDefinitionsFlow.value = it
            //_wordDefinitionLiveData.postValue(data)
        }.launchIn(this)
    }



    //fun getDefinitions(word:String) = getWordDefinitions(word)
}