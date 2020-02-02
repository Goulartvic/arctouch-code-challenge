package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("genre/movie/list")
    fun genres(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.DEFAULT_LANGUAGE
    ): Observable<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(
            @Query("page") page: Long,
            @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
            @Query("language") language: String = BuildConfig.DEFAULT_LANGUAGE
    ): Observable<UpcomingMoviesResponse>

    @GET("movie/{id}")
    fun movie(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = BuildConfig.DEFAULT_LANGUAGE
    ): Observable<Movie>
}
