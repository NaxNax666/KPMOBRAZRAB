package com.example.kpmobrazrab

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kpmobrazrab.databinding.FragmentMirrorHorizAxisBinding


class mirrorHorizAxisFragment : Fragment() {

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
        binding.srcImageMHA.setImageResource(R.drawable.img1)
        binding.executeButtonMHA.setOnClickListener {

            val bitmap = (binding.srcImageMHA.getDrawable() as BitmapDrawable).bitmap

            binding.destImageMHA.setImageBitmap(CoreSystem.MirrorHorizAxis(bitmap))
        }
    }
}