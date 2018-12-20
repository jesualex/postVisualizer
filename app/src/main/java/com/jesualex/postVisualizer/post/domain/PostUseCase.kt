package com.jesualex.postVisualizer.post.domain

import com.jesualex.postVisualizer.utils.UseCase
import com.jesualex.postVisualizer.post.data.remote.PostList
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.post.data.remote.PostApi
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Created by jesualex on 19-12-18.
 */

class PostUseCase : UseCase<MutableList<Post>>() {
    override fun createObservableUseCase(): Observable<MutableList<Post>> {
        return PostApi.get().searchByDate("android").map(Function<PostList, MutableList<Post>> {
            return@Function it.hits
        })
    }
}