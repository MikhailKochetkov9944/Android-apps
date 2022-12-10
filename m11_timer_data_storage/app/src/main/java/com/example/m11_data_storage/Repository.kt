package com.example.m11_data_storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences



class Repository {
    private val PREFERENCE_NAME = "prefs_name"
    val SHARED_PREFS_KEY = "shared_prefs_key"
    var localVariable: String? = null
    lateinit var prefs: SharedPreferences


    private fun getDataFromSharedPreference(context: Context): String? {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return prefs.getString(SHARED_PREFS_KEY, null)
    }
    private fun getDataFromLocalVariable(): String? {
        return localVariable
    }
    fun saveText(text: String, context: Context) {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        prefs.edit().putString(SHARED_PREFS_KEY, text).commit()
        localVariable = text
    }
    fun clearText(): String? {
        prefs.edit().putString(SHARED_PREFS_KEY, null)
        localVariable = null
        return null
    }
    fun getText(context: Context): String {
        return when{
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference(context) != null -> getDataFromSharedPreference(context)!!
            else -> "no one source doesn't contain string"
        }
    }
}
