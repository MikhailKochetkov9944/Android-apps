package com.example.m12_news_feed.ui.main

sealed class State {
    object Start: State()
    object Loading: State()
    object Success: State()
}