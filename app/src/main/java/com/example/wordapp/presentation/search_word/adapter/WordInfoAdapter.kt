package com.example.wordapp.presentation.search_word.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordapp.R
import com.example.wordapp.databinding.WordInfoContentViewTypeBinding
import com.example.wordapp.databinding.WordInfoHeadingViewTypeBinding
import com.example.wordapp.domain.model.WordData
import com.example.wordapp.domain.model.WordDataType
import com.example.wordapp.util.firstLetterUpperCase

class WordInfoAdapter(private val wordDataList: List<WordData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class WordInfoHeadingViewHolder(val binding: WordInfoHeadingViewTypeBinding) :
        RecyclerView.ViewHolder(binding.root)

    class WordInfoContentViewHolder(val binding: WordInfoContentViewTypeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == WordDataType.HEADING.ordinal) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.word_info_heading_view_type, parent, false)
            val binding = WordInfoHeadingViewTypeBinding.bind(view)
            WordInfoHeadingViewHolder(binding)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.word_info_content_view_type, parent, false)
            val binding = WordInfoContentViewTypeBinding.bind(view)
            WordInfoContentViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WordInfoHeadingViewHolder)
            holder.binding.wordDataHeadingTxt.text =
                wordDataList[position].data.firstLetterUpperCase()
        else if (holder is WordInfoContentViewHolder)
            holder.binding.wordDataContentTxt.text =
                wordDataList[position].data.firstLetterUpperCase()

    }

    override fun getItemCount(): Int {
        return wordDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return wordDataList[position].type.ordinal
    }

}