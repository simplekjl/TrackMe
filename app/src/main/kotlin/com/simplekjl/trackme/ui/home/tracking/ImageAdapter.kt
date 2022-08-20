package com.simplekjl.trackme.ui.home.tracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
        val requestOption = RequestOptions()
            .placeholder(R.drawable.placeholder)
            .centerCrop()
        Glide.with(binding.root.context)
            .load(url)
            .apply(requestOption)
            .into(binding.image)
    }
}