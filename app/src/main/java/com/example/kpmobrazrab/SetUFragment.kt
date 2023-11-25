package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentSetUBinding


class SetUFragment : Fragment() {
    lateinit var binding: FragmentSetUBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetUBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageSU.setImageBitmap(Storage.bitmap)

        binding.executeButtonSU.setOnClickListener {

            val blue = binding.strenghtEditTextSU.text.toString().toInt()

            (binding.srcImageSU.drawable as? BitmapDrawable?)?.let {
                binding.srcImageSU.setImageBitmap(CoreSystem.SetU(it.bitmap, blue))
            }
        }
    }
}