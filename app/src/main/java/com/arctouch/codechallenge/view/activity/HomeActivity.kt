package com.arctouch.codechallenge.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.view.adapter.HomeAdapter
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    private val itemOnClick: (View, Int, Int) -> Unit = { view, position, type ->
        startActivity(
                Intent(this, MovieDetailActivity::class.java)
                        .putExtra("id", adapter.getItem(position).id)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        observeViewModel()

        viewModel.getGenres()
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.moviesLiveData.observe(this, Observer {
            adapter = HomeAdapter(it, itemOnClick)
            recyclerView.adapter = adapter
            progressBar.visibility = View.GONE
        })

        viewModel.genresLiveData.observe(this, Observer {
            Cache.cacheGenres(it)
            viewModel.getUpcomingMovies()
        })
    }
}
