package com.dicoding.moviecatalogue_made.fav.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.ui.MovieAdapter
import com.dicoding.moviecatalogue_made.fav.di.favoriteModule
import com.dicoding.moviecatalogue_made.detail.DetailActivity
import com.dicoding.moviecatalogue_made.fav.databinding.FragmentFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private var _favoriteBinding: FragmentFavoriteBinding? = null
    private val binding get() = _favoriteBinding

    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val viewModel: FavMovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _favoriteBinding = FragmentFavoriteBinding.inflate(
            layoutInflater,
            container, false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        binding?.progressBar?.visibility = View.VISIBLE
        viewModel.getFavMovie().observe(viewLifecycleOwner) { movies ->
            movieAdapter.setData(movies)
            showDataFavorite(movies)
        }

        movieAdapter.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, it)
            startActivity(intent)
        }

        with(binding?.rvListMovie) {
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }
    }

    private fun showDataFavorite(movies: List<Movie>) {
        if (movies.isEmpty()) {
            binding?.apply {
                progressBar.visibility = View.GONE
                ivEmptyList.visibility = View.VISIBLE
                rvListMovie.visibility = View.GONE
            }
        } else {
            binding?.apply {
                progressBar.visibility = View.GONE
                ivEmptyList.visibility = View.GONE
                rvListMovie.visibility = View.VISIBLE
            }
        }
    }
}