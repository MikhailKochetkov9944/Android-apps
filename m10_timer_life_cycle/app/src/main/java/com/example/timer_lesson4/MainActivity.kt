package com.example.timer_lesson4

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.timer_lesson4.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    var maxValue = 0
    var currentProgress = 0
    var isActiveTimer = false
    lateinit var binding: ActivityMainBinding
    var scope = CoroutineScope(Job() + Dispatchers.Main)
    var job: Job? = null
    var jobSecond: Job? = null
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
            job?.start()
            jobSecond?.start()
        }
        setDefault()

        binding.slider.addOnChangeListener { _, value,_ ->
            maxValue = value.toInt()
            currentProgress = value.toInt()
            binding.progressBarCircular.max = value.toInt()
            binding.textCount.text = maxValue.toString()
            binding.buttonStart.isEnabled = value > 0
        }

        binding.buttonStart.setOnClickListener {
                if(!isActiveTimer) {
                    binding.slider.isEnabled = false
                    isActiveTimer = true
                    binding.buttonStart.setText(R.string.stop)
                    job = scope.launch {
                        Log.d(TAG, "onCreate: thread - ${Thread.currentThread().name}, " +
                                "scope - ${CoroutineScope(Dispatchers.Main).isActive}")
                        timerStart()
                        binding.slider.isEnabled = true
                        binding.textCount.text = binding.slider.value.toString()
                        binding.progressBarCircular.progress = binding.slider.value.toInt()
                        isActiveTimer = false
                        binding.buttonStart.setText(R.string.start)
                        Toast.makeText(applicationContext, "Timer finished!", Toast.LENGTH_SHORT)
                            .show()
                        job?.cancel()
                    }
                    jobSecond = scope.launch {

                            Log.d(TAG, "onCreate2: thread - ${Thread.currentThread().name}, " +
                                    "scope - ${CoroutineScope(Dispatchers.Main).isActive}")
                            if(!isActiveTimer) {
                                job?.cancel()
                                updateView(currentProgress)
                            } else {
                                job?.start()
                            }
                    }
                } else {
                    currentProgress = 0
                    setDefault()
                    binding.buttonStart.setText(R.string.start)
                    isActiveTimer = false
                    binding.slider.isEnabled = true
                    Toast.makeText(applicationContext, "Timer finished!", Toast.LENGTH_SHORT).show()
                    job?.cancel()
                }
        }
    }
    private suspend fun timerStart() {
        while(currentProgress > 0) {
            currentProgress--
            updateView(currentProgress)
            delay(1000)

        }
    }
    private suspend fun updateView(currentProgress: Int) {
        withContext(Dispatchers.Main) {
            binding.textCount.text = currentProgress.toString()
            binding.progressBarCircular.progress = currentProgress
        }
    }
    private fun setDefault() {
        binding.textCount.text = currentProgress.toString()
        binding.progressBarCircular.progress = currentProgress
        binding.progressBarCircular.max = currentProgress
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

    override fun onDestroy() {
        job?.cancel()
        jobSecond?.cancel()
        super.onDestroy()
    }
}