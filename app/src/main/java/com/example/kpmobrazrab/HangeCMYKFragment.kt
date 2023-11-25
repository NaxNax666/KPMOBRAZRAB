package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentChangeCMYKBinding


class HangeCMYKFragment : Fragment() {
    lateinit var binding: FragmentChangeCMYKBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangeCMYKBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.srcImageCCMYK.setImageBitmap(Storage.bitmap)

        binding.executeButtonCCMYK.setOnClickListener {

            val c = binding.cEditTextGB.text.toString().toInt()
            val m = binding.mEditText.text.toString().toInt()
            val y = binding.yEditText.text.toString().toInt()
            val k = binding.kEditText.text.toString().toInt()

            (binding.srcImageCCMYK.drawable as? BitmapDrawable?)?.let {
                binding.destImageCCMYK.setImageBitmap(CoreSystem.ChangeCMYK(it.bitmap, c, m, y,k))
            }
        }
    }
}