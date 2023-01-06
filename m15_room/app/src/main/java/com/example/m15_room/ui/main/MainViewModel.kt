package com.example.m15_room.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val wordDao: WordDao) : ViewModel() {
    val allWords = this.wordDao.getWords()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun onAddBtn(text: String) {
        Log.d("DTCR", "onCreate: start fun onAddBtn with word: $text")
        viewModelScope.launch {
            wordDao.insert(
                Word(
                    word = text,
                    count = 5
                )
            )
            Log.d("DTCR", "onCreate: addWord")
        }
    }
    fun onUpdateBtn(word: String) {
        viewModelScope.launch {
            wordDao.update(word)
        }
    }

}