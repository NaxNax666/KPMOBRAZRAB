package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentCutColorsBinding


class cutColorsFragment : Fragment() {
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

        binding.srcImageCC.setImageResource(R.drawable.img1)

        binding.executeButtonCC.setOnClickListener {
            
            val strength = binding.strenghtEditTextCC.text.toString().toInt()

            val bitmap = (binding.srcImageCC.getDrawable() as BitmapDrawable).bitmap

            binding.destImageCC.setImageBitmap(CoreSystem.CutColors(bitmap, strength))
        }
    }
}