package com.example.kpmobrazrab

import MAIN
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImage.setImageResource(R.drawable.img1)
        ArrayAdapter.createFromResource(this.requireContext(), R.array.function_array, android.R.layout.simple_spinner_item).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.chooseSpinner.adapter = adapter
        }

        binding.startButton.setOnClickListener {
            val choose = binding.chooseSpinner.selectedItemPosition
            Log.d("Navigate", MAIN.navController.currentDestination?.id.toString())
            if(MAIN.navController.currentDestination?.id == R.id.mainFragment) {
                when (choose) {
                    0 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_blackWhiteFragment)
                    }

                    1 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_addNoizeFragment)
                    }

                    2 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_cutColorsFragment)
                    }

                    3 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_pixelizationFragment)
                    }

                    4 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_gaussianBlurFragment)
                    }

                    5 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_mirrorVertAxisFragment)
                    }

                    6 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_mirrorHorizAxisFragment)
                    }
                    7 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_mirrorBothAxisFragment)
                    }
                    8 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_changeRGBFragment)
                    }
                    9 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_setRedFragment)
                    }
                    10 -> {
                        MAIN.navController.navigate(R.id.action_mainFragment_to_setGreenFragment)
                    }

                }
            }
        }
    }
}