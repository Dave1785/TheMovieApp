package com.funapps.themovie.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.funapps.themovie.R
import com.funapps.themovie.data.model.Popular
import com.funapps.themovie.extensions.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w1280/"
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var profileImageView: ImageView
    private lateinit var profileName: TextView
    private lateinit var knownFor: TextView
    private lateinit var popularity: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileMoviesAdapter = ProfileMovieAdapter()
        val profileMoviesRV = view.findViewById<RecyclerView>(R.id.profile_movies_rv)

        profileMoviesRV.layoutManager = LinearLayoutManager(requireContext())
        profileMoviesRV.adapter = profileMoviesAdapter

        profileImageView = view.findViewById(R.id.profile_iv)

        profileName = view.findViewById(R.id.profile_name_tv)
        popularity = view.findViewById(R.id.popularity_tv)
        knownFor = view.findViewById(R.id.know_for_tv)

        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.popularList.collect { mostPopular ->
                mostPopular?.let {
                    populateData(it)
                    profileMoviesAdapter.submitList(it.knowFor)
                }
            }
        }

    }

    private fun populateData(profile: Popular) {
        profileImageView.load(
            imageUrl = BASE_URL_IMAGE + profile.profilePath,
            placeholder = R.drawable.place_holder_profile,
            error = R.drawable.placeholder_error
        )
        profileName.text = "Name: ${profile.name}"
        popularity.text = "Popularity: ${profile.popularity}%"
        knownFor.text = "Profesion: ${profile.knowForDepartment}"
    }

}