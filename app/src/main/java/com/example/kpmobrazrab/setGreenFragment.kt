package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentSetGreenBinding


class setGreenFragment : Fragment() {
    lateinit var binding: FragmentSetGreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetGreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageSG.setImageResource(R.drawable.img1)

        binding.executeButtonSG.setOnClickListener {

            val green = binding.strenghtEditTextSG.text.toString().toInt()

            val bitmap = (binding.srcImageSG.getDrawable() as BitmapDrawable).bitmap

            binding.destImageSG.setImageBitmap(CoreSystem.SetGreen(bitmap, green))
        }
    }
}