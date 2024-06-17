package com.funapps.themovie.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.funapps.themovie.R

class LocationsAdapter(private val locations: List<LocationData>) :
    RecyclerView.Adapter<LocationsAdapter.LocationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.nameTextView.text = location.name
        holder.dateTextView.text = location.formattedDate
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView
        var dateTextView: TextView

        init {
            nameTextView = itemView.findViewById(R.id.nameTextView)
            dateTextView = itemView.findViewById(R.id.dateTextView)
        }
    }
}

