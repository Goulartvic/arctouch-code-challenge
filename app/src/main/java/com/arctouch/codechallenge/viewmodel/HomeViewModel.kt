package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.BaseApplication
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : BaseViewModel() {

    var moviesLiveData = MutableLiveData<List<Movie>>()
    var genresLiveData = MutableLiveData<List<Genre>>()
    var apiInstance = BaseApplication.apiInstance

    fun getUpcomingMovies() {

        apiInstance.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    moviesLiveData.value = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                }
    }

    fun getGenres() {
        apiInstance.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    genresLiveData.value = it.genres
                }
    }

}