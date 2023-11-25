package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentPixelizationBinding


class PixelizationFragment : Fragment() {
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

        binding.srcImageP.setImageBitmap(Storage.bitmap)

        binding.executeButtonP.setOnClickListener {

            val strength = binding.strenghtEditTextP.text.toString().toInt()

            (binding.srcImageP.drawable as? BitmapDrawable?)?.let {
                binding.srcImageP.setImageBitmap(CoreSystem.Pixelization(it.bitmap, strength))
            }
        }
    }
}