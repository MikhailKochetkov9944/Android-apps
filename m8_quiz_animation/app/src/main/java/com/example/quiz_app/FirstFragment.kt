package com.example.quiz_app

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quiz_app.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}