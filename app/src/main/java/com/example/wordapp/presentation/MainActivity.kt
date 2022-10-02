package com.example.wordapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wordapp.R
import com.example.wordapp.presentation.search_word.SearchWordFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, SearchWordFragment.newInstance())
            commit()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}