package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentAutoLumAndConRegBinding


class AutoLumAndConRegFragment : Fragment() {
    lateinit var binding: FragmentAutoLumAndConRegBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAutoLumAndConRegBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageALACRB.setImageBitmap(Storage.bitmap)
        binding.executeButtonALACRB.setOnClickListener {
            (binding.srcImageALACRB.getDrawable() as? BitmapDrawable?)?.let {
                binding.destImageALACRB.setImageBitmap(CoreSystem.AutoLumAndConReg(it.bitmap))
            }
        }
    }
}