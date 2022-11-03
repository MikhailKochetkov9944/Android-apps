package com.example.viewgroup_lesson3

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.viewgroup_lesson3.databinding.CustomPostBinding

class Post @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
): LinearLayout(context, attrs, defStyleAttr) {
    val binding = CustomPostBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setText1(text: String) {
      binding.text1.text = text
    }
    fun setText2(text: String) {
        binding.text2.text = text
    }
}