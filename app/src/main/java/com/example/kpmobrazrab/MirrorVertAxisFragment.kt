package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentMirrorVertAxisBinding


class MirrorVertAxisFragment : Fragment() {
    lateinit var binding: FragmentMirrorVertAxisBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMirrorVertAxisBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageMVA.setImageBitmap(Storage.bitmap)
        binding.executeButtonMVA.setOnClickListener {

            (binding.srcImageMVA.drawable as? BitmapDrawable?)?.let {
                binding.srcImageMVA.setImageBitmap(CoreSystem.MirrorVertAxis(it.bitmap))
            }
        }
    }
}