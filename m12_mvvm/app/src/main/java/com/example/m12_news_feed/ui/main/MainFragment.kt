package com.example.m12_news_feed.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.m12_news_feed.R
import com.example.m12_news_feed.databinding.FragmentMainBinding
import kotlinx.coroutines.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: FragmentMainBinding
    lateinit var inputText: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null) {
            with(savedInstanceState) {
                inputText = getString("textResult").toString()

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.searchButton.setOnClickListener {
             inputText = binding.editText.text.toString()
            viewModel.onSignOnClick()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect{ state: State ->
                when(state) {
                    State.Start -> {
                        binding.progress.isVisible = false
                        binding.searchButton.isEnabled = false
                        binding.resultText.text = getString(R.string.text1)
                    }
                    State.Loading -> {
                        binding.progress.isVisible = true
                        binding.searchButton.isEnabled = false
                    }
                    State.Success -> {
                        binding.progress.isVisible = false
                        binding.searchButton.isEnabled = true
                        binding.resultText.text = "По запросу <$inputText> ничего не найдено"
                    }

                }

            }
        }

        binding.editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0 != null) {
                    viewModel.isEnabledButton(p0)
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })


            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.isEnabledButton(binding.editText.text.toString())
                viewModel.stateText.collect { stateText: StateText ->
                    when(stateText) {
                        StateText.Empty -> {
                            binding.searchButton.isEnabled = false
                        }
                        StateText.Full -> {
                            binding.searchButton.isEnabled = true
                        }
                    }
                }
            }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("textResult", inputText)
        super.onSaveInstanceState(outState)
    }
}