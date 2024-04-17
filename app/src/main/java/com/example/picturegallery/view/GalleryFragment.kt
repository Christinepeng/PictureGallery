package com.example.picturegallery.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.example.picturegallery.R
import com.example.picturegallery.databinding.FragmentGalleryBinding
import com.example.picturegallery.viewModel.GalleryViewModel


class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("bear", "GalleryFragment onActivityCreated()")
        val galleryAdapter = GalleryAdapter()
        binding.recyclerView.apply {
            Log.e("bear", "GalleryFragment recyclerView")

            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            Log.e("bear", "GalleryFragment recyclerView apply finish")

        }

        val galleryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GalleryViewModel::class.java)
        galleryViewModel.photoListLive.observe(viewLifecycleOwner, Observer {
            galleryAdapter.submitList(it)
        })

        galleryViewModel.photoListLive.value ?: galleryViewModel.fetchData()
        Log.e("bear", "GalleryFragment onActivityCreated() finish")

    }
}