package com.jesualex.postVisualizer.post.data.remote

import com.jesualex.postVisualizer.utils.ApiServiceFactory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by jesualex on 19-12-18.
 */

interface PostApi{
    @GET("search_by_date") fun searchByDate(@Query("query") query : String) : Observable<PostList>

    companion object {
        private const val URL = "https://hn.algolia.com/api/v1/"
        private var API : PostApi? = null

        fun get() : PostApi {
            return API ?: run {
                API =
                        ApiServiceFactory.build(
                            PostApi::class.java,
                            URL
                        )
                return API as PostApi
            }
        }
    }
}