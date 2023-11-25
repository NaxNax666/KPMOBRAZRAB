package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentNegativeRGBBinding


class NegativeRGBFragment : Fragment() {
    lateinit var binding: FragmentNegativeRGBBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNegativeRGBBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageNRGB.setImageBitmap(Storage.bitmap)
        binding.executeButtonNRGB.setOnClickListener {

            (binding.srcImageNRGB.drawable as? BitmapDrawable?)?.let {
                runCatching {
                    CoreSystem.NegativeRGB(it.bitmap, 1,1,1)
                }.onFailure {
                    // Log fail "java.lang.ArrayIndexOutOfBoundsException: length=1100; index=1417"
                }.onSuccess {
                    // Do on success
                    // Also ".getOrThrow()", ".getOrElse()", ".getOrDefault()". Etc.
                }.getOrNull()?.let {
                    binding.srcImageNRGB.setImageBitmap(it)
                }
            }
        }
    }
}