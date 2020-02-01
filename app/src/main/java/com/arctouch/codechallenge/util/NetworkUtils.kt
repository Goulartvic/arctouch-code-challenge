package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.api.TmdbApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkUtils {

    companion object {

        fun getInstance(): TmdbApi {
            return Retrofit.Builder()
                    .baseUrl(TmdbApi.URL)
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(TmdbApi::class.java)
        }
    }
}
