package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentNegativeRGBBinding


class negativeRGBFragment : Fragment() {
    lateinit var binding: FragmentNegativeRGBBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNegativeRGBBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageNRGB.setImageResource(R.drawable.img1)
        binding.executeButtonNRGB.setOnClickListener {

            val bitmap = (binding.srcImageNRGB.getDrawable() as BitmapDrawable).bitmap

            binding.destImageNRGB.setImageBitmap(CoreSystem.NegativeRGB(bitmap, 1,1,1))
        }
    }
}