package com.jesualex.postVisualizer.post.data.entityUtils

import com.jesualex.postVisualizer.App
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.post.data.entity.Post_
import io.objectbox.Box
import io.objectbox.query.Query


/**
 * Created by jesualex on 19-12-18.
 */

object PostUtils {
    private lateinit var box: Box<Post>

    private fun getBox(): Box<Post> {
        if (!::box.isInitialized) {
            box = App.boxStore.boxFor(Post::class.java)
        }

        return box;
    }

    fun getQueryByDate(): Query<Post> {
        return getBox().query().run {
            equal(Post_.deleted, false)
            orderDesc(Post_.created_at)
        }.build()
    }

    fun getQueryByDeleted() : Query<Post>{
        return getBox().query().run {
            equal(Post_.deleted, true)
        }.build()
    }

    fun getPostsDeleted() : MutableList<Post>{
        return getQueryByDeleted().find()
    }

    fun put(post: Post) {
        getBox().put(post)
    }

    fun put(posts: MutableList<Post>) {
        getBox().put(posts)
    }
}