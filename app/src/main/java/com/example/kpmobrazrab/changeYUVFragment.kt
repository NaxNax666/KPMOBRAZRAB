package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentChangeYUVBinding


class changeYUVFragment : Fragment() {
    lateinit var binding: FragmentChangeYUVBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangeYUVBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageCYUV.setImageBitmap(DataClass._bitmap)

        binding.executeButtoncYUV.setOnClickListener {

            val red = binding.YEditTextYUV.text.toString().toInt()
            val green = binding.UEditText.text.toString().toInt()
            val blue = binding.VEditText.text.toString().toInt()

            val bitmap = (binding.srcImageCYUV.getDrawable() as BitmapDrawable).bitmap

            binding.destImageCYUV.setImageBitmap(CoreSystem.ChangeYUV(bitmap, red, green, blue))
        }
    }
}