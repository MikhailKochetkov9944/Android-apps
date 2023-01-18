package com.example.m15_room.ui.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {
    @Transaction
    @Query("SELECT * FROM wordTable LIMIT 5")
    fun getWords(): Flow<List<Word>>

    @Insert(entity = Word::class, onConflict = IGNORE)
    suspend fun insert(word: Word)

    @Query("Delete FROM wordTable")
    suspend fun delete()

    @Query("UPDATE wordTable SET count = count + 1 WHERE word LIKE :element")
    suspend fun update(element: String)
}