package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.BaseApplication
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : BaseViewModel() {

    var moviesLiveData = MutableLiveData<List<Movie>>()
    var searchLiveData = MutableLiveData<List<Movie>>()
    private var apiInstance = BaseApplication.apiInstance

    fun getUpcomingMovies(page: Long) {
        apiInstance.upcomingMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    moviesLiveData.postValue(it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    })
                }
    }

    fun searchMovies(title: String) {
        apiInstance.movieByName(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    searchLiveData.postValue(it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    })
                }
    }
}