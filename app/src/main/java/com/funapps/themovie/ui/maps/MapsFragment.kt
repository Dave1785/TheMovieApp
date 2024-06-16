package com.funapps.themovie.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.funapps.themovie.R
import com.funapps.themovie.network.ResultType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return  inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.textView)

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED){
                mapsViewModel.getPopularList(1)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mapsViewModel.popularList.collect { result ->
                when(result){
                    is ResultType.Error -> {

                    }
                    is ResultType.Success -> {
                        textView.text = result.data.results[0].name
                    }
                }
            }
        }

    }

}