package com.dicoding.moviecatalogue_made.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.core.domain.model.Movie
import com.dicoding.moviecatalogue_made.BuildConfig
import com.dicoding.moviecatalogue_made.R
import com.dicoding.moviecatalogue_made.databinding.ActivityDetailBinding
import com.jakewharton.rxbinding2.view.clicks
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {


    private val detailViewModel: DetailViewModel by viewModel()
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailMovie = intent.getParcelableExtra<Movie>(EXTRA_ID)
        if (detailMovie != null) {
            loadDataDetail(detailMovie)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_share -> {
                shareMovie()
                true
            }
            else -> true
        }
    }

    @SuppressLint("CheckResult")
    private fun loadDataDetail(movie: Movie) {
        binding.apply {
            tvTitle.text = movie.title
            tvRelease.text = movie.releaseDate
            tvPopularity.text = movie.popularity
            tvRating.text = movie.voteAverage.toString()
            tvDesc.text = movie.overview
            Glide.with(this@DetailActivity)
                .load(BuildConfig.IMAGES + "/${movie.posterPath}")
                .into(ivMovie)
            Glide.with(this@DetailActivity)
                .load(BuildConfig.IMAGES + "/${movie.backdropPath}")
                .into(ivPoster)

            var statusFavorite = movie.isFavorite
            setFavorite(statusFavorite)
            fabFavorite.clicks().subscribe {
                statusFavorite = !statusFavorite
                detailViewModel.setFavMovie(movie, statusFavorite)
                setFavorite(statusFavorite)
            }
        }
    }

    private fun setFavorite(state: Boolean) {
        if (state) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this, R.drawable.ic_fav
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this, R.drawable.ic_fav_border
                )
            )
        }
    }

    private fun shareMovie() {
        val shareMovie = "${R.string.Recommendation} \n" +
                "${R.string.title} : ${binding.tvTitle.text} \n" +
                "${R.string.rating} : ${binding.tvRating.text}\n" +
                "${R.string.desc} : ${binding.tvDesc.text}\n"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMovie)
        shareIntent.type = "text/plain"
        startActivity(Intent.createChooser(shareIntent, "Bagikan dengan"))
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}