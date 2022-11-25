package com.example.quiz_app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.quiz_app.databinding.CustomViewBinding

class CustomViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
    ): LinearLayout(context, attrs, defStyleAttr) {
        val binding = CustomViewBinding.inflate(LayoutInflater.from(context))

    init{
        addView(binding.root)
    }
}