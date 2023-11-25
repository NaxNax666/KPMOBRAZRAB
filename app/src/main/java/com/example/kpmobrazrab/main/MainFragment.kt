package com.example.kpmobrazrab.main

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.R
import com.example.kpmobrazrab.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // https://developer.android.com/topic/libraries/view-binding#fragments
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private var router: MainRouter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /**
         * Fragment attaches to the host's activity context. The MainActivity in this case.
         * This means we can check if this context implements some things.
         *
         * With a Single Activity pattern
         * this also helps to share some core logic between hosted fragments.
         *
         * In this project, MainFragment plays role of a MainMenu
         * and don't provides any business logic.
         *
         * So consider to change navigation to all other feature-fragments
         * as to a child screens of MainFragment.
         * Or refactor MainFragment as MainMenu
         */
        router = context as? MainRouter?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setImage(Storage.bitmap)
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.function_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.chooseSpinner.adapter = adapter
        }
        binding.openFileButton.setOnClickListener { setImage(Storage.bitmap) }
        binding.startButton.setOnClickListener { onNavigate() }
    }

    override fun onDestroyView() {
        // After destroy view this dependency is no longer valid. Should clear it
        _binding = null

        super.onDestroyView()
    }

    override fun onDetach() {
        // Should release dependency on global component
        // to allow garbage collector handle the MainFragment
        router = null

        super.onDetach()
    }

    private fun onNavigate() {
        router?.navigateForward(binding.chooseSpinner.selectedItemPosition)
    }

    private fun setImage(bitmap: Bitmap?) {
        bitmap?.let { binding.srcImage.setImageBitmap(it) }
    }
}