package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
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

        binding.srcImageSV.setImageBitmap(Storage.bitmap)

        binding.executeButtonSV.setOnClickListener {

            val blue = binding.strenghtEditTextSV.text.toString().toInt()

            (binding.srcImageSV.drawable as? BitmapDrawable?)?.let {
                binding.srcImageSV.setImageBitmap(CoreSystem.SetV(it.bitmap, blue))
            }
        }
    }
}