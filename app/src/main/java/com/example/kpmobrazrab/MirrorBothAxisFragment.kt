package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.data.CoreSystem
import com.example.kpmobrazrab.data.Storage
import com.example.kpmobrazrab.databinding.FragmentMirrorBothAxisBinding


class MirrorBothAxisFragment : Fragment() {
    lateinit var binding: FragmentMirrorBothAxisBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMirrorBothAxisBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srcImageMBA.setImageBitmap(Storage.bitmap)
        binding.executeButtonMBA.setOnClickListener {

            (binding.destImageMBA.drawable as? BitmapDrawable?)?.let {
                binding.destImageMBA.setImageBitmap(CoreSystem.MirrorBothAxis(it.bitmap))
            }
        }
    }
}