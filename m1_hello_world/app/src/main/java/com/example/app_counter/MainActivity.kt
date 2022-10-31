package com.example.app_counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.app_counter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var count = 0
    var num = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.plusOneButton.setOnClickListener {
             if(count in 0..48 ) {
                count++
                num = 50 - count
                binding.textView1.visibility = View.INVISIBLE
                binding.textView2.text = "Осталось мест: $num"
                binding.textView2.visibility = View.VISIBLE
                binding.textView3.visibility = View.INVISIBLE
                binding.minusOneButton.isEnabled = true
            } else if(count >= 49) {
                count++
                binding.textView1.visibility = View.INVISIBLE
                binding.textView2.visibility = View.INVISIBLE
                binding.textView3.visibility = View.VISIBLE
                binding.buttonSb.visibility = View.VISIBLE
                binding.minusOneButton.isEnabled = true
            }
            binding.textCount.text = count.toString()
        }
        binding.buttonSb.setOnClickListener {
            count = 0
            binding.textView1.text = "Все места свободны"
            binding.textCount.text = count.toString()
            binding.minusOneButton.isEnabled = false
            binding.textView1.visibility = View.VISIBLE
            binding.textView2.visibility = View.INVISIBLE
            binding.textView3.visibility = View.INVISIBLE
        }
        binding.minusOneButton.setOnClickListener {
            if(count == 0) {
                 binding.minusOneButton.isEnabled = false

            } else if(count == 1) {
                count--
                binding.textView1.visibility = View.VISIBLE
                binding.textView2.visibility = View.INVISIBLE
                binding.textView3.visibility = View.INVISIBLE
            }
            else if(count in 2..50 ) {
                count--
                num = 50 - count
                binding.textView1.visibility = View.INVISIBLE
                binding.textView2.text = "Осталось мест: $num"
                binding.textView2.visibility = View.VISIBLE
                binding.textView3.visibility = View.INVISIBLE
            } else if(count >= 51) {
                count--
                binding.textView1.visibility = View.INVISIBLE
                binding.textView2.visibility = View.INVISIBLE
                binding.textView3.visibility = View.VISIBLE
                binding.buttonSb.visibility = View.VISIBLE
            }
            binding.textCount.text = count.toString()
        }
    }
}