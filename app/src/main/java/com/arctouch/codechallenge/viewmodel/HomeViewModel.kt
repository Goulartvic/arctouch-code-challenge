package com.arctouch.codechallenge.viewmodel

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.NetworkUtils
import com.arctouch.codechallenge.view.activity.HomeActivity
import com.arctouch.codechallenge.view.adapter.HomeAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.*

class HomeViewModel : BaseViewModel() {

    var moviesLiveData = MutableLiveData<List<Movie>>()
    var genresLiveData = MutableLiveData<List<Genre>>()

    fun getUpcomingMovies() {
        NetworkUtils.getInstance().upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    moviesLiveData.value = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                }
    }

    fun getGenres() {
        NetworkUtils.getInstance().genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    genresLiveData.value = it.genres
                }
    }

}