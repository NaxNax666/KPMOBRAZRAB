package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentConvolutionWithMatrBinding


class convolutionWithMatrFragment : Fragment() {
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

        binding.srcImageCWM.setImageResource(R.drawable.img1)

        binding.executeButtonCWM.setOnClickListener {

            val mode = binding.strenghtEditTextCWM.text.toString().toInt()

            val bitmap = (binding.srcImageCWM.getDrawable() as BitmapDrawable).bitmap

            binding.destImageCWM.setImageBitmap(CoreSystem.ConvolutionWithMatr(bitmap, mode))
        }
    }
}