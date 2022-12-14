package com.example.m12_news_feed.ui.main

sealed class StateText {
    object Empty: StateText()
    object Full: StateText()
}