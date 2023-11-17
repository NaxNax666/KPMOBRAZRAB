package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentMirrorBothAxisBinding


class mirrorBothAxisFragment : Fragment() {
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
        binding.srcImageMBA.setImageResource(R.drawable.img1)
        binding.executeButtonMBA.setOnClickListener {

            val bitmap = (binding.srcImageMBA.getDrawable() as BitmapDrawable).bitmap

            binding.destImageMBA.setImageBitmap(CoreSystem.MirrorBothAxis(bitmap))
        }
    }
}