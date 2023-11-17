package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentPixelizationBinding


class pixelizationFragment : Fragment() {
    lateinit var binding: FragmentPixelizationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPixelizationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageP.setImageResource(R.drawable.img1)

        binding.executeButtonP.setOnClickListener {

            val strength = binding.strenghtEditTextP.text.toString().toInt()

            val bitmap = (binding.srcImageP.getDrawable() as BitmapDrawable).bitmap

            binding.destImageP.setImageBitmap(CoreSystem.Pixelization(bitmap, strength))
        }
    }
}