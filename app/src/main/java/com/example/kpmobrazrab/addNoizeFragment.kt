package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentAddNoizeBinding
import java.lang.Math.abs

class addNoizeFragment : Fragment() {

    lateinit var binding: FragmentAddNoizeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNoizeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageAWN.setImageResource(R.drawable.img1)
        binding.executeButtonAWN.setOnClickListener {

            val middle = binding.middleEditText.text.toString().toInt()
            val strength = binding.strenghtEditText.text.toString().toInt()
            if (abs(middle) > 256){
                return@setOnClickListener
            }


            val bitmap = (binding.srcImageAWN.getDrawable() as BitmapDrawable).bitmap

            binding.destImageAWN.setImageBitmap(CoreSystem.AddWhiteNoise(bitmap, middle, strength))
        }
    }


}