package com.arctouch.codechallenge.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.viewmodel.MovieDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_detail_activity.*
import kotlinx.android.synthetic.main.movie_detail_activity.progressBar

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel
    private val movieImageUrlBuilder = MovieImageUrlBuilder()
    private lateinit var urls: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_activity)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.extras?.getInt("id", 0)

        observeViewModel()

        viewModel.getMovie(movieId!!.toLong())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

        viewModel.movieLiveData.observe(this, Observer {
            setupCarousel(it)

            tvMovieName.text = it.title
            tvMovieGenres.text = baseContext.getString(
                    R.string.movie_genres, it.genres?.joinToString(separator = ", ") { it.name }
            )
            tvReleaseDate.text = baseContext.getString(R.string.release_date, it.releaseDate)
            tvMovieOverview.text = it.overview

            progressBar.visibility = View.GONE
            nestedScrollView.visibility = View.VISIBLE
        })
    }

    private fun setupCarousel(movie: Movie) {
        urls = arrayOf(movieImageUrlBuilder.buildPosterUrl(movie.posterPath!!),
                movieImageUrlBuilder.buildBackdropUrl(movie.backdropPath!!))

        cvMovieImage.setImageListener { position, imageView ->
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            Glide.with(imageView)
                    .load(urls[position])
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(imageView)

        }
        cvMovieImage.pageCount = urls.size
    }
}