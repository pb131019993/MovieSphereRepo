package com.example.moviesphere.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesphere.R
import com.example.moviesphere.beans.response.PersonImage

class PersonImageAdapter(private var images: List<PersonImage>) :
    RecyclerView.Adapter<PersonImageAdapter.PersonImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person_image, parent, false)
        return PersonImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonImageViewHolder, position: Int) {
        holder.bind(images[position].file_path)
    }

    override fun getItemCount(): Int = images.size

    fun updateImages(newImages: List<PersonImage>) {
        images = newImages
        notifyDataSetChanged()
    }

    class PersonImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.person_image)

        fun bind(imageUrl: String) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500$imageUrl")
                .into(imageView)
        }
    }
}