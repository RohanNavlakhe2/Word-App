package com.example.wordapp.presentation.search_word

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordapp.R
import com.example.wordapp.databinding.SearchWordFragmentBinding
import com.example.wordapp.domain.model.Word
import com.example.wordapp.domain.model.WordData
import com.example.wordapp.presentation.search_word.adapter.WordInfoAdapter
import com.example.wordapp.util.Resource
import com.example.wordapp.util.firstLetterUpperCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import timber.log.Timber

@AndroidEntryPoint
class SearchWordFragment : Fragment(R.layout.search_word_fragment) {

    private val searchWordViewModel by viewModels<SearchWordViewModel>()
    private lateinit var searchWordFragmentBinding: SearchWordFragmentBinding
    private lateinit var wordInfoAdapter: WordInfoAdapter
    private var wordInfoList = mutableListOf<WordData>()
    private val searchFlow = MutableStateFlow("")
    private val textWatcher by lazy {
        SearchWordTextWatcher(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchWordFragmentBinding = SearchWordFragmentBinding.bind(view)
        initRec()
        observeWordDefinitionsFlow()
        searchWord()
    }

    /*private fun searchWord() {

        val searchFlow = MutableStateFlow("")

        lifecycleScope.launchWhenStarted {
            searchFlow.debounce(500)
                .filter { searchQuery -> searchQuery.trim().isNotEmpty() }
                .distinctUntilChanged()
                //distinctUntilChanged() -> If last emitted value and new value is same then it won't pass the
                // new value to flatMapLatest.
                //So if user enters "bat" (takes 500 ms pause,this way api will be called with "bat")
                // then removes "t" from "bat" then again adds "t", so the latest word is "bat"
                // (which is similar to last word the api was called for)
                // then this newly entered "bat" won't go to the flatMapLatest(),
                //and so will prevent unnecessary api call
                .flatMapLatest { searchQuery ->
                    searchWordViewModel.getDefinitions(searchQuery)
                    return@flatMapLatest searchWordViewModel.wordDefinitionsFlow
                }
                //flatMapLatest() -> Passes the latest value emitted by the flow
                // (which we're returning from this method) and ignores previously emitted values
                // This way we won't be updating the UI again and again in collectLatest()
                .collectLatest {
                    handleResult(it!!)
                }
        }

        searchWordFragmentBinding.searchWordET.doAfterTextChanged {
            searchFlow.value = it?.toString()?.trim() ?: ""
        }
    }*/

    private fun searchWord() {

        lifecycleScope.launchWhenStarted {
            searchFlow.debounce(500)
                .filter { searchQuery -> searchQuery.trim().isNotEmpty() }
                .distinctUntilChanged()
                //distinctUntilChanged() -> If last emitted value and new value is same then it won't pass the
                // new value to flatMapLatest.
                //So if user enters "bat" (takes 500 ms pause,this way api will be called with "bat")
                // then removes "t" from "bat" then again adds "t", so the latest word is "bat"
                // (which is similar to last word the api was called for)
                // then this newly entered "bat" won't go to the flatMapLatest(),
                //and so will prevent unnecessary api call
                .collectLatest { searchQuery ->
                    searchWordViewModel.getDefinitions(searchQuery)
                }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleResult(result: Resource<Word>) {
        when (result) {
            is Resource.Loading -> {
                searchWordFragmentBinding.progressBar.visibility = View.VISIBLE
            }
            is Resource.Success -> {
                searchWordFragmentBinding.progressBar.visibility = View.GONE
                result.data?.let { word ->
                    searchWordFragmentBinding.wordTxt.text = word.word.firstLetterUpperCase()
                    wordInfoList.clear()
                    wordInfoList.addAll(word.wordDataList)
                    wordInfoAdapter.notifyDataSetChanged()
                }
            }
            is Resource.Error -> {
                searchWordFragmentBinding.progressBar.visibility = View.GONE
                Toast.makeText(activity, result.message ?: "Error", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun initRec() {
        wordInfoAdapter = WordInfoAdapter(wordInfoList)
        searchWordFragmentBinding.wordInfoRec.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = wordInfoAdapter
        }
    }


    private fun observeWordDefinitionsFlow() {
        //We should use launchWhenStarted when working with StateFlow
         lifecycleScope.launchWhenStarted {
            searchWordViewModel.wordDefinitionsFlow.collectLatest {
                if (it == null)
                    Timber.tag(TAG).d("result is null")
                else
                    handleResult(it)
            }
        }
    }

    companion object {
        private const val TAG = "SearchWordFragment"
    }

    override fun onStart() {
        super.onStart()
        searchWordFragmentBinding.searchWordET.addTextChangedListener(textWatcher)
    }

    override fun onStop() {
        searchWordFragmentBinding.searchWordET.removeTextChangedListener(textWatcher)
        super.onStop()
    }

    class SearchWordTextWatcher(private val searchWordFragment: SearchWordFragment):TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            searchWordFragment.searchFlow.value = s?.toString()?.trim() ?: ""
        }

    }



}


