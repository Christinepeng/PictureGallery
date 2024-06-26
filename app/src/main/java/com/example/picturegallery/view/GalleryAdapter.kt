package com.example.picturegallery.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.picturegallery.R
import com.example.picturegallery.model.PhotoItem
import io.supercharge.shimmerlayout.ShimmerLayout

class GalleryAdapter : ListAdapter<PhotoItem, MyViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val holder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.gallery_cell, parent, false))
        holder.itemView.setOnClickListener {
            Bundle().apply {
                putParcelable("PHOTO", getItem(holder.adapterPosition))
                holder.itemView.findNavController().navigate(R.id.action_galleryFragment_to_photoFragment, this)
            }

        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val shimmerLayout: ShimmerLayout = holder.itemView.findViewById(R.id.shimmerLayoutCell)
        shimmerLayout.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }

        val imageView: ImageView = holder.itemView.findViewById(R.id.imageView)
        Glide.with(holder.itemView)
            .load(getItem(position).previewUrl)
            .placeholder(R.drawable.ic_photo_gray_24dp)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false.also { shimmerLayout.stopShimmerAnimation() }
                }
            })
            .into(imageView)

    }

    object DIFFCALLBACK: DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId
        }
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)