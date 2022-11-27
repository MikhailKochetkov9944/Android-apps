package com.example.quiz_app

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.quiz_app.databinding.FragmentFirstBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd-MM-yy")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        (AnimatorInflater.loadAnimator(context, R.animator.custom_animation) as ObjectAnimator)
            .apply {
                target = binding.startButton
                start()
            }
        (AnimatorInflater.loadAnimator(context, R.animator.animation_rotation) as ObjectAnimator)
            .apply {
                target = binding.startButton
                start()
            }
        binding!!.buttonDateOfBirth.setOnClickListener {
            val constraints = CalendarConstraints.Builder()
                .setOpenAt(calendar.timeInMillis)
                .build()
            val dateDialog = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraints)
                .setTitleText(resources.getString(R.string.date_dialog_title))
                .build()


            dateDialog.addOnPositiveButtonClickListener { timeInMillis ->
                calendar.timeInMillis = timeInMillis
//                val day = calendar.get(Calendar.DAY_OF_MONTH)
//                val month = calendar.get(Calendar.MONTH)
//                val year = calendar.get(Calendar.YEAR)
//                val text = "$day / $month / $year"
                Snackbar.make(binding!!.buttonDateOfBirth, dateFormat.format(calendar.time), Snackbar.LENGTH_SHORT).show()
            }

            dateDialog.show(childFragmentManager, "DatePicker")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}