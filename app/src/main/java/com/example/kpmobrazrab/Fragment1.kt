package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.Fragment1Binding




class Fragment1 : Fragment() {

    lateinit var binding: Fragment1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = Fragment1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imageView.setImageResource(R.drawable.img1)
        binding.executeButton.setOnClickListener {

            val bitmap = (binding.imageView.getDrawable() as BitmapDrawable).bitmap

            binding.imageView2.setImageBitmap(CoreSystem.DoNothing(bitmap))
        }
    }

}