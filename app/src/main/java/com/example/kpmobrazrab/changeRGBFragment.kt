package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentChangeRGBBinding


class changeRGBFragment : Fragment() {
    lateinit var binding: FragmentChangeRGBBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangeRGBBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageCRGB.setImageBitmap(DataClass._bitmap)

        binding.executeButtoncRGB.setOnClickListener {

            val red = binding.redEditTextGB.text.toString().toInt()
            val green = binding.greenEditText.text.toString().toInt()
            val blue = binding.blueEditText.text.toString().toInt()

            val bitmap = (binding.srcImageCRGB.getDrawable() as BitmapDrawable).bitmap

            binding.destImageCRGB.setImageBitmap(CoreSystem.ChangeRGB(bitmap, red, green, blue))
        }
    }
}