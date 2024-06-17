package com.funapps.themovie.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.funapps.themovie.R
import com.funapps.themovie.data.model.KnowFor
import com.funapps.themovie.extensions.load

private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w1280/"
class ProfileMovieAdapter : ListAdapter<KnowFor, ProfileMovieAdapter.ItemViewHolder>(ItemDiffCallback()) {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePosterImage: ImageView = itemView.findViewById(R.id.movie_iv)
        val movieTitle: TextView = itemView.findViewById(R.id.profile_movie_tv)
        val movieDescription: TextView = itemView.findViewById(R.id.overview_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_profile_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.movieTitle.text = currentItem.originalTitle
        holder.movieDescription.text = currentItem.overview
        holder.moviePosterImage.load(
            imageUrl = BASE_URL_IMAGE + currentItem.posterPath,
            placeholder = R.drawable.placeholder_movie,
            error = R.drawable.placeholder_error
        )

    }

}

internal class ItemDiffCallback : DiffUtil.ItemCallback<KnowFor>() {
    override fun areItemsTheSame(oldItem: KnowFor, newItem: KnowFor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: KnowFor, newItem: KnowFor): Boolean {
        return oldItem == newItem
    }
}