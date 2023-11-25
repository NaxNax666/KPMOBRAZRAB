package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentSetGreenBinding


class SetGreenFragment : Fragment() {
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

        binding.srcImageSG.setImageBitmap(Storage.bitmap)

        binding.executeButtonSG.setOnClickListener {

            val green = binding.strenghtEditTextSG.text.toString().toInt()

            (binding.srcImageSG.drawable as? BitmapDrawable?)?.let {
                binding.srcImageSG.setImageBitmap(CoreSystem.SetGreen(it.bitmap, green))
            }
        }
    }
}