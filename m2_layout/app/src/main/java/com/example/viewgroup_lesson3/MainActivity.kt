package com.example.viewgroup_lesson3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewgroup_lesson3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.post.setText1("Первая строка")
        binding.post.setText2("Вторая строка")
    }
}