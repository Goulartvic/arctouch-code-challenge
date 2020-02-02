package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.BaseApplication
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel : ViewModel() {

    var movieLiveData = MutableLiveData<Movie>()
    private var apiInstance = BaseApplication.apiInstance

    fun getMovie(movieId: Long) {
        apiInstance.movie(movieId, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    movieLiveData.postValue(it)
                }
    }
}