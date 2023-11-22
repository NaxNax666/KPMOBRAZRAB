package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentSetVBinding


class SetVFragment : Fragment() {
    lateinit var binding: FragmentSetVBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetVBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageSV.setImageBitmap(DataClass._bitmap)

        binding.executeButtonSV.setOnClickListener {

            val blue = binding.strenghtEditTextSV.text.toString().toInt()

            val bitmap = (binding.srcImageSV.getDrawable() as BitmapDrawable).bitmap

            binding.destImageSV.setImageBitmap(CoreSystem.SetV(bitmap, blue))
        }
    }
}