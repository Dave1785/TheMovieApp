package com.funapps.themovie.ui.movies

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.funapps.themovie.R
import com.funapps.themovie.data.model.Movie
import com.funapps.themovie.extensions.load
import kotlin.math.roundToInt

private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w1280/"
class MoviesAdapter : ListAdapter<Movie, MoviesAdapter.ItemViewHolder>(ItemDiffCallback()) {

    // ViewHolder class
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePosterImage: ImageView = itemView.findViewById(R.id.poster_movie)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        val movieDescription: TextView = itemView.findViewById(R.id.movie_description)
        val voteAveragePb: ProgressBar = itemView.findViewById(R.id.vote_average_pb)
        val voteAverageTv: TextView = itemView.findViewById(R.id.vote_average_tv)
    }

    // Inflate the item layout and create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ItemViewHolder(itemView)
    }

    // Bind data to the item layout
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.movieTitle.text = currentItem.title
        holder.movieDescription.text = currentItem.overview
        holder.voteAveragePb.progress = currentItem.voteAverage.roundToInt()
        holder.voteAverageTv.text = "${currentItem.voteAverage.roundToInt()}0%"


        holder.moviePosterImage.load(
            imageUrl = BASE_URL_IMAGE + currentItem.posterPath,
            placeholder = R.drawable.placeholder_movie,
            error = R.drawable.placeholder_error
        )

    }

}

internal class ItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        // Compare item IDs
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        // Compare all fields
        return oldItem == newItem
    }
}