package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentSetRedBinding


class SetRedFragment : Fragment() {
    lateinit var binding: FragmentSetRedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetRedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageSR.setImageBitmap(Storage.bitmap)

        binding.executeButtonSR.setOnClickListener {

            val red = binding.strenghtEditTextSR.text.toString().toInt()

            (binding.srcImageSR.drawable as? BitmapDrawable?)?.let {
                binding.srcImageSR.setImageBitmap(CoreSystem.SetRed(it.bitmap, red))
            }
        }
    }
}