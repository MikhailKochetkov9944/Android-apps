package com.example.m15_room.ui.main

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao
}