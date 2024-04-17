package com.example.picturegallery.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
    private lateinit var galleryViewModel: GalleryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("bear", "GalleryFragment, onCreateView()")

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("bear", "GalleryFragment, onOptionsItemSelected()")
        when(item.itemId) {
            R.id.swipeIndicator -> {
                binding.swipeLayoutGallery.isRefreshing = true
                Handler().postDelayed(Runnable {galleryViewModel.fetchData()}, 1000)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        setHasOptionsMenu(true)
        val galleryAdapter = GalleryAdapter()
        binding.recyclerView.apply {

            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)

        }

        galleryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GalleryViewModel::class.java)
        galleryViewModel.photoListLive.observe(viewLifecycleOwner, Observer {
            galleryAdapter.submitList(it)
            binding.swipeLayoutGallery.isRefreshing = false
        })

        galleryViewModel.photoListLive.value ?: galleryViewModel.fetchData()

        binding.swipeLayoutGallery.setOnRefreshListener{
            galleryViewModel.fetchData()
        }
    }
}