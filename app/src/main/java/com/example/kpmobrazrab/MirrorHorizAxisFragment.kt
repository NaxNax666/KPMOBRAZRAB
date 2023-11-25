package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentMirrorHorizAxisBinding


class MirrorHorizAxisFragment : Fragment() {

    lateinit var binding: FragmentMirrorHorizAxisBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMirrorHorizAxisBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageMHA.setImageBitmap(Storage.bitmap)
        binding.executeButtonMHA.setOnClickListener {

            (binding.srcImageMHA.drawable as? BitmapDrawable?)?.let {
                binding.srcImageMHA.setImageBitmap(CoreSystem.MirrorHorizAxis(it.bitmap))
            }
        }
    }
}