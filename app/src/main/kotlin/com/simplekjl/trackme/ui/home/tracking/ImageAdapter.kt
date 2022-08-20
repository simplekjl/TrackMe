package com.simplekjl.trackme.ui.home.tracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.simplekjl.trackme.R
import com.simplekjl.trackme.databinding.ImageCardBinding

class ImageAdapter(val photos: MutableList<String>) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            ImageCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

}

class ImageViewHolder(private val binding: ImageCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(url: String) {
        binding.image.load(url) {
            crossfade(true)
            placeholder(R.drawable.placeholder)
            error(R.drawable.placeholder)
            scale(Scale.FIT)
        }
    }
}