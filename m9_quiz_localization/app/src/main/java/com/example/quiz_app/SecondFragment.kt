package com.example.quiz_app

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.navigation.fragment.findNavController
import com.example.quiz_app.databinding.FragmentSecondBinding
import com.example.quiz_app.quiz.QuizStorage
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var questionView = QuizStorage.getQuiz(QuizStorage.Locale.En).questions
    private var questionList = mutableListOf<CustomViewGroup>()
    private val answers = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun getResult(): MutableList<String> {
        var result = mutableListOf<String>()
        questionList.forEach {
                answers.add(it.binding.radioGroup.checkedRadioButtonId)
        }
        if (Locale.getDefault().language == "ru") {
            result.add(QuizStorage.answer(QuizStorage.getQuiz(QuizStorage.Locale.Ru), answers))
        } else if (Locale.getDefault().language == "en") {
            result.add(QuizStorage.answer(QuizStorage.getQuiz(QuizStorage.Locale.En), answers))
        }
        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(Locale.getDefault().language == "ru") {
            questionView = QuizStorage.getQuiz(QuizStorage.Locale.Ru).questions
        }
        questionView.forEach {
            val group = CustomViewGroup(requireContext())
            group.binding.questionText.text = it.question
            it.answers.forEachIndexed { index, text ->
                val radioBtn = RadioButton(context)
                radioBtn.id = index
                radioBtn.text = text
                radioBtn.textSize = 20.0F
                group.binding.radioGroup.addView(radioBtn)
                group.binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                    if (radioBtn.id >= 0) {
                        Toast.makeText(
                            context,
                            "${group.binding.radioGroup.checkedRadioButtonId}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (radioBtn.id == -1) {
                        !binding.answerButton.isClickable
                        Toast.makeText(
                            context,
                            "set all",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            binding.LinearLayoutQuiz.addView(group)
            questionList.add(group)
        }

//        questionView.forEach {
//            val textView = TextView(context)
//            textView.text = it.question
//            val radioGr = RadioGroup(context)
//            it.answers.forEachIndexed { index, text ->
//                val radioBtn = RadioButton(context)
//                radioBtn.id = index
//                radioBtn.text = text
//                radioGr.addView(radioBtn)
//            }
//            _binding!!.LinearLayoutQuiz.addView(textView)
//            _binding!!.LinearLayoutQuiz.addView(radioGr)
//        }

        binding.answerButton.setOnClickListener {
            try {
                val resultList = getResult()
                val action =
                    SecondFragmentDirections.actionSecondFragmentToThirdFragment(resultList.toString())
                findNavController().navigate(action)
            } catch (e: Throwable) {
                Toast.makeText(context, "Ответьте на все вопросы", Toast.LENGTH_SHORT).show()

//
            }
        }
        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        ObjectAnimator.ofFloat(binding.answerButton, View.ALPHA, 0f,1f)
            .apply {
                duration = 4000
                start()
            }
        ObjectAnimator.ofFloat(binding.backButton, View.ALPHA, 0f,1f)
            .apply {
                duration = 4000
                start()
            }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

}
