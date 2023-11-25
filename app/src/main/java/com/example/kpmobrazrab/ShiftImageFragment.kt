package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentShiftImageBinding


class ShiftImageFragment : Fragment() {
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

        binding.srcImageSI.setImageBitmap(Storage.bitmap)

        binding.executeButtonSI.setOnClickListener {

            val right = binding.rightEditTextSI.text.toString().toInt()
            val up = binding.upEditText.text.toString().toInt()

            (binding.srcImageSI.drawable as? BitmapDrawable?)?.let {
                binding.srcImageSI.setImageBitmap(CoreSystem.ShiftImage(it.bitmap, right, up))
            }
        }
    }
}