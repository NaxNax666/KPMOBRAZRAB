package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentShiftImageBinding


class shiftImageFragment : Fragment() {
    lateinit var binding: FragmentShiftImageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShiftImageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageSI.setImageBitmap(DataClass._bitmap)

        binding.executeButtonSI.setOnClickListener {

            val right = binding.rightEditTextSI.text.toString().toInt()
            val up = binding.upEditText.text.toString().toInt()

            val bitmap = (binding.srcImageSI.getDrawable() as BitmapDrawable).bitmap

            binding.destImageSI.setImageBitmap(CoreSystem.ShiftImage(bitmap, right, up))
        }
    }
}