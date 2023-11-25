package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentSetYBinding


class SetYFragment : Fragment() {
    lateinit var binding: FragmentSetYBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetYBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageSY.setImageBitmap(Storage.bitmap)

        binding.executeButtonSY.setOnClickListener {

            val blue = binding.strenghtEditTextSY.text.toString().toInt()

            (binding.srcImageSY.drawable as? BitmapDrawable?)?.let {
                binding.srcImageSY.setImageBitmap(CoreSystem.SetY(it.bitmap, blue))
            }
        }
    }
}