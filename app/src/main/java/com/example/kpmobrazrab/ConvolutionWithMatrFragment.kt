package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentConvolutionWithMatrBinding


class ConvolutionWithMatrFragment : Fragment() {
    lateinit var binding: FragmentConvolutionWithMatrBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConvolutionWithMatrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageCWM.setImageBitmap(Storage.bitmap)

        binding.executeButtonCWM.setOnClickListener {

            /**
             * Here and all other inputs:
             * >java.lang.NumberFormatException: For input string: ""
             *
             * Check input values, add filters, add regexp,
             * and default values or use try-catch - runCatching {}
             */
            val mode = binding.strenghtEditTextCWM.text.toString().let {
                if (it.isEmpty()) 0 else it.toInt()
            }

            (binding.destImageCWM.drawable as? BitmapDrawable?)?.let {
                binding.destImageCWM.setImageBitmap(CoreSystem.ConvolutionWithMatr(it.bitmap, mode))
            }
        }
    }
}