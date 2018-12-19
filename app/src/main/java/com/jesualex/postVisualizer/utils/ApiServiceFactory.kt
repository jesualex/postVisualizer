package com.jesualex.postVisualizer.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jesualex on 19-12-18.
 */

object ApiServiceFactory {
    fun <T> build(serviceClass: Class<T>, urlBase: String, client: OkHttpClient? = null): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        client?.let { retrofit.client(client) }

        return retrofit.build().create(serviceClass)
    }
}