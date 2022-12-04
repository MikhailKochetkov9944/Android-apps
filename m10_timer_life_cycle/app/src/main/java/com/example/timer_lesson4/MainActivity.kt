package com.example.timer_lesson4

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import com.example.timer_lesson4.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    //var maxValue = 0
    var currentProgress = 0
    var isActiveTimer = false
    lateinit var binding: ActivityMainBinding
    var scope = CoroutineScope(Job() + Dispatchers.Main)
    var job: Job? = null
    var jobSecond: Job? = null
    var jobView: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState != null) {
            with(savedInstanceState) {
                currentProgress = getInt("progress")
                Log.d("DTCR", "currentProgress(save) = $currentProgress")
                isActiveTimer = getBoolean("activeTimer")
                binding.slider.isEnabled = getBoolean("sliderIsEn")
                //binding.slider.value = getFloat("sliderValue")
                binding.textCount.text = getCharSequence("text")
                Log.d("DTCR", "textCount(save) = ${binding.textCount.text}")
                binding.progressBarCircular.progress = getInt("progressBar")
                Log.d("DTCR", "progressBarCircular(save) = ${binding.progressBarCircular.progress}")
                binding.buttonStart.text = getCharSequence("buttonStartText")
            }
            //job?.start()
            //jobSecond?.start()

        }
        setDefault()
        binding.slider.addOnChangeListener { _, _, _ ->
            if(savedInstanceState == null || !isActiveTimer) {
            currentProgress = binding.slider.value.toInt()
            }
            binding.progressBarCircular.max = binding.slider.value.toInt()
            //binding.progressBarCircular.progress = currentProgress
            binding.textCount.text = currentProgress.toString()
            binding.buttonStart.isEnabled = currentProgress > 0
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
                        binding.textCount.text = currentProgress.toString()
                        binding.progressBarCircular.progress = currentProgress
                        isActiveTimer = false
                        binding.buttonStart.setText(R.string.start)
                        Toast.makeText(applicationContext, "Timer finished!1", Toast.LENGTH_SHORT)
                            .show()
                        job?.cancel()
                    }
//                    jobSecond = scope.launch {
//
//                            Log.d(TAG,
//                                    "scope - ${CoroutineScope(Dispatchers.Main).isActive}")
//                            if(!isActiveTimer) {
//                                job?.cancel()
//                                updateView(currentProgress)
//                            } else {
//                                job?.start()
//                            }
//                    }
                } else {
                    currentProgress = 0
                    setDefault()
                    binding.buttonStart.setText(R.string.start)
                    isActiveTimer = false
                    binding.slider.isEnabled = true
                    Toast.makeText(applicationContext, "Timer finished!2", Toast.LENGTH_SHORT).show()
                    job?.cancel()
                }
        }
    }
    private suspend fun timerStart() {
        while(currentProgress > 0) {
            currentProgress--
            Log.d("DTCR", "currentProgress = $currentProgress")
//            binding.textCount.text = currentProgress.toString()
//            Log.d("DTCR", "textCount = ${binding.textCount.text}")
//            binding.progressBarCircular.progress = currentProgress
            updateView(currentProgress)
            delay(1000)

        }
    }
    private suspend fun updateView(millis: Int) {
        withContext(Dispatchers.Main) {
            binding.textCount.text = millis.toString()
            Log.d("DTCR", "textCount(update) = ${binding.textCount.text}")
            binding.progressBarCircular.progress = millis
            Log.d("DTCR", "progressBarCircular(update) = ${binding.progressBarCircular.progress}")
        }
    }
    private fun setDefault() {
        binding.textCount.text = currentProgress.toString()
        Log.d("DTCR", "textCount(default) = ${binding.textCount.text}")
        binding.progressBarCircular.progress = currentProgress
        Log.d("DTCR", "progressBarCircular(default) = ${binding.progressBarCircular.progress}")
        binding.progressBarCircular.max = currentProgress
    }


    override fun onSaveInstanceState(outState: Bundle) {
        //outState.run {
            outState.putInt("progress", currentProgress)
            outState.putBoolean("activeTimer", isActiveTimer)
            outState.putBoolean("sliderIsEn", binding.slider.isEnabled)
            //putFloat("sliderValue", binding.slider.value)
            outState.putCharSequence("text", binding.textCount.text)
            outState.putInt("progressBar", binding.progressBarCircular.progress)
            outState.putCharSequence("buttonStartText", binding.buttonStart.text)

        //}
        super.onSaveInstanceState(outState)

    }

    override fun onDestroy() {
        //job?.cancel()
        //jobSecond?.cancel()
        super.onDestroy()
    }
}