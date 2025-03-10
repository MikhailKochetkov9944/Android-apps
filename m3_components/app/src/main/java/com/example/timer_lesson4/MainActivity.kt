package com.example.timer_lesson4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.timer_lesson4.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    var maxValue = 0
    var currentProgress = 0
    var isActiveTimer = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.slider.addOnChangeListener { _, value,_ ->
            maxValue = value.toInt()
            currentProgress = value.toInt()
            binding.progressBarCircular.max = value.toInt()
            binding.textCount.text = maxValue.toString()
            binding.buttonStart.isEnabled = value > 0
        }
        binding.buttonStart.setOnClickListener {
            val start = CoroutineScope(Job() + Dispatchers.Main)


            start.launch {
                if(!isActiveTimer) {
                    binding.slider.isEnabled = false
                    isActiveTimer = true
                    binding.buttonStart.setText(R.string.stop)
                    for(i in 0..maxValue) {
                        currentProgress --
                        if(currentProgress <= 0) {
                            binding.slider.isEnabled = true
                            binding.slider.value = 0F
                            isActiveTimer = false
                            binding.buttonStart.setText(R.string.start)
                            Toast.makeText(applicationContext, "Timer finished!", Toast.LENGTH_SHORT).show()
                            start.cancel()
                            return@launch
                        }
                        binding.textCount.text = currentProgress.toString()
                        binding.progressBarCircular.progress = currentProgress
                        delay(1000)
                    }
                } else {
                    binding.buttonStart.setText(R.string.start)
                    isActiveTimer = false
                    binding.slider.isEnabled = true
                    binding.slider.value = 0F
                    Toast.makeText(applicationContext, "Timer finished!", Toast.LENGTH_SHORT).show()
                    start.cancel()
                }
            }
        }
    }
}