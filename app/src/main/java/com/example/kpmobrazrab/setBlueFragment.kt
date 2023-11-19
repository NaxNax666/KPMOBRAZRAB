package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentSetBlueBinding


class setBlueFragment : Fragment() {
    lateinit var binding: FragmentSetBlueBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetBlueBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageSB.setImageResource(R.drawable.img1)

        binding.executeButtonSB.setOnClickListener {

            val blue = binding.strenghtEditTextSB.text.toString().toInt()

            val bitmap = (binding.srcImageSB.getDrawable() as BitmapDrawable).bitmap

            binding.destImageSB.setImageBitmap(CoreSystem.SetBlue(bitmap, blue))
        }
    }
}