package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentBlackWhiteBinding


class blackWhiteFragment : Fragment() {

    lateinit var binding: FragmentBlackWhiteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlackWhiteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageWB.setImageBitmap(DataClass._bitmap)
        binding.executeButtonWB.setOnClickListener {

            val bitmap = (binding.srcImageWB.getDrawable() as BitmapDrawable).bitmap

            binding.destImageWB.setImageBitmap(CoreSystem.ConvertToBlackWhite(bitmap))
        }
    }


}