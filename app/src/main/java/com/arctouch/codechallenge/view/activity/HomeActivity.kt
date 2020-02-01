package com.arctouch.codechallenge.view.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.view.adapter.HomeAdapter
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : BaseActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        observeViewModel()

        viewModel.getGenres()
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.moviesLiveData.observe(this, Observer {
            recyclerView.adapter = HomeAdapter(it)
            progressBar.visibility = View.GONE
        })

        viewModel.genresLiveData.observe(this, Observer {
            Cache.cacheGenres(it)
            viewModel.getUpcomingMovies()
        })
    }
}
