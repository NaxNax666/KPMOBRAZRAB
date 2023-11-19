package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentSobelOperatorBinding


class sobelOperatorFragment : Fragment() {
    lateinit var binding: FragmentSobelOperatorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSobelOperatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageSO.setImageResource(R.drawable.img1)
        binding.executeButtonSO.setOnClickListener {

            val bitmap = (binding.srcImageSO.getDrawable() as BitmapDrawable).bitmap

            binding.destImageSO.setImageBitmap(CoreSystem.SobelOperator(bitmap))
        }
    }
}