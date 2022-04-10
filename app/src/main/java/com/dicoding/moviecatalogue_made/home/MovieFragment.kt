package com.dicoding.moviecatalogue_made.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.ui.MovieAdapter
import com.dicoding.core.utils.SortUtils.BEST_VOTE
import com.dicoding.core.utils.SortUtils.WORST_VOTE
import com.dicoding.moviecatalogue_made.R
import com.dicoding.moviecatalogue_made.databinding.FragmentMovieBinding
import com.dicoding.moviecatalogue_made.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {
    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding

    private val viewModel: MovieViewModel by viewModel()
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }

    private var sort = BEST_VOTE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _fragmentMovieBinding = FragmentMovieBinding.inflate(
            layoutInflater, container, false
        )
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            this?.rvListMovie?.setHasFixedSize(true)
            this?.rvListMovie?.adapter = movieAdapter
            this?.progressBar?.visibility = View.VISIBLE
        }

        movieAdapter.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, it)
            startActivity(intent)
        }

        viewModel.getMovie(BEST_VOTE).observe(viewLifecycleOwner, movieObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_home, menu)
        when (sort) {
            BEST_VOTE -> menu.findItem(R.id.menu_sort_best).isChecked = true
            WORST_VOTE -> menu.findItem(R.id.menu_sort_worst).isChecked = true
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort_best -> sort = BEST_VOTE
            R.id.menu_sort_worst -> sort = WORST_VOTE
        }

        item.isChecked = true
        viewModel.getMovie(sort).observe(viewLifecycleOwner, movieObserver)

        return super.onOptionsItemSelected(item)
    }

    private val movieObserver = Observer<Resource<List<Movie>>> { movie ->
        if (movie != null) {
            when (movie) {
                is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    movieAdapter.setData(movie.data)
                }
                is Resource.Error -> {
                    binding?.progressBar?.visibility = View.GONE
                    binding?.ivEmptyList?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentMovieBinding = null
    }
}