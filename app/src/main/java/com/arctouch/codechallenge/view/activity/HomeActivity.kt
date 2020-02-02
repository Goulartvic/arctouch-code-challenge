package com.arctouch.codechallenge.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.view.adapter.HomeAdapter
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private val adapter by lazy { HomeAdapter() }
    private var isScrolling = false
    private var page: Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        recyclerView.adapter = adapter
        adapter.setOnItemClickListener {
            startActivity(
                    Intent(this, MovieDetailActivity::class.java)
                            .putExtra("id", it.id)
            )
        }

        observeViewModel()

        setupScrollListener(recyclerView)

        viewModel.getUpcomingMovies(page)
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.moviesLiveData.observe(this, Observer {
            progressBar.visibility = View.GONE
            val auxiliarList: MutableList<Movie> = mutableListOf()
            auxiliarList.addAll(adapter.data)
            auxiliarList.addAll(it)
            adapter.data = auxiliarList
        })
    }

    private fun setupScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView
                        .layoutManager as LinearLayoutManager
                val currentItems = layoutManager.childCount
                val totalItems = layoutManager.itemCount
                val scrollOutItems = layoutManager.findFirstVisibleItemPosition()
                val expectedTotal = scrollOutItems.let { currentItems.plus(it) }
                if (isScrolling && expectedTotal == totalItems) {
                    isScrolling = false
                    page = page.plus(1)
                    viewModel.getUpcomingMovies(page)
                }
            }
        })
    }
}
