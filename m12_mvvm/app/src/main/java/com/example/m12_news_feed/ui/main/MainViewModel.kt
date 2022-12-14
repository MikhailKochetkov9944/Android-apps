package com.example.m12_news_feed.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Start)
    val state = _state.asStateFlow()

    private val _stateText = MutableStateFlow<StateText>(StateText.Empty)
    val stateText = _stateText.asStateFlow()


    fun onSignOnClick() {
        viewModelScope.launch {
            _state.value = State.Loading
            delay(5000)
            _state.value = State.Success
        }
    }
    fun isEnabledButton(text: CharSequence) {
        viewModelScope.launch {
                if(text.length < 3) {
                    _stateText.value = StateText.Empty
                } else {
                    _stateText.value = StateText.Full
                }
        }
    }
}