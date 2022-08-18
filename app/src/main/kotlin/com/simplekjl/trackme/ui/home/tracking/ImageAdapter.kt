package com.simplekjl.trackme.ui.home.tracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.simplekjl.trackme.databinding.ImageCardBinding
import kotlin.collections.ArrayDeque

class ImageAdapter(val photos: ArrayDeque<String>) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            ImageCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(photos.elementAt(position))
    }

    override fun getItemCount(): Int = photos.size

}

class ImageViewHolder(val binding: ImageCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(url: String) {
        binding.image.load(url)
    }
}