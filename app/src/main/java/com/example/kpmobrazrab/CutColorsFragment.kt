package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentCutColorsBinding


class CutColorsFragment : Fragment() {
    lateinit var binding: FragmentCutColorsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCutColorsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageCC.setImageBitmap(Storage.bitmap)

        binding.executeButtonCC.setOnClickListener {
            
            val strength = binding.strenghtEditTextCC.text.toString().toInt()

            (binding.destImageCC.drawable as? BitmapDrawable?)?.let {
                binding.destImageCC.setImageBitmap(CoreSystem.CutColors(it.bitmap, strength))
            }
        }
    }
}