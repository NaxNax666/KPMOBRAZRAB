package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentRobertsCrossBinding


class robertsCrossFragment : Fragment() {
    lateinit var binding: FragmentRobertsCrossBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRobertsCrossBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageRO.setImageBitmap(DataClass._bitmap)
        binding.executeButtonRO.setOnClickListener {

            val bitmap = (binding.srcImageRO.getDrawable() as BitmapDrawable).bitmap

            binding.destImageRO.setImageBitmap(CoreSystem.RobertsCross(bitmap))
        }
    }
}