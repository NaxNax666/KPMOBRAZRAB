package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentBlackWhiteBinding


class BlackWhiteFragment : Fragment() {

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
        binding.srcImageWB.setImageBitmap(Storage.bitmap)
        binding.executeButtonWB.setOnClickListener {
            (binding.srcImageWB.drawable as? BitmapDrawable?)?.let {
                binding.srcImageWB.setImageBitmap(CoreSystem.ConvertToBlackWhite(it.bitmap))
            }
        }
    }
}