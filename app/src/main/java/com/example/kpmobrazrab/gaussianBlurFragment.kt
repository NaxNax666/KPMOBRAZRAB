package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentGaussianBlurBinding


class gaussianBlurFragment : Fragment() {
    lateinit var binding: FragmentGaussianBlurBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGaussianBlurBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageGB.setImageResource(R.drawable.img1)

        binding.executeButtonGB.setOnClickListener {

            val size = binding.sizeEditTextGB.text.toString().toInt()
            val sigma = binding.sigmaEditText.text.toString().toDouble()

            val bitmap = (binding.srcImageGB.getDrawable() as BitmapDrawable).bitmap

            binding.destImageGB.setImageBitmap(CoreSystem.GaussianBlur(bitmap, size, sigma))
        }
    }
}