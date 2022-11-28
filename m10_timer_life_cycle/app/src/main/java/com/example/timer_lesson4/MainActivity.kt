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
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState != null) {
            with(savedInstanceState) {
                currentProgress = getInt("progress")
                isActiveTimer = getBoolean("activeTimer")
                //maxValue = getInt("maxValue")
                binding.slider.isEnabled = getBoolean("sliderIsEn")
                //binding.slider.value = getFloat("sliderValue")
                binding.textCount.text = getCharSequence("text")
                binding.progressBarCircular.progress = getInt("progressBar")
                binding.buttonStart.text = getCharSequence("buttonStartText")

            }
        }

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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt("progress", currentProgress)
            putBoolean("activeTimer", isActiveTimer)
            //putInt("maxValue", maxValue)
            putBoolean("sliderIsEn", binding.slider.isEnabled)
            //putFloat("sliderValue", binding.slider.value)
            putCharSequence("text", binding.textCount.text)
            putInt("progressBar", binding.progressBarCircular.progress)
            putCharSequence("buttonStartText", binding.buttonStart.text)

        }
        super.onSaveInstanceState(outState)

    }
}