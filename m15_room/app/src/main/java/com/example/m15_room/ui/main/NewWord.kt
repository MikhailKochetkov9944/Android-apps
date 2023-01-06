package com.example.m15_room.ui.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordTable")
class NewWord(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "count")
    val count: Int
)


