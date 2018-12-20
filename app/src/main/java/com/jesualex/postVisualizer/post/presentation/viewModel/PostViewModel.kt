package com.jesualex.postVisualizer.post.presentation.viewModel

import android.arch.lifecycle.ViewModel
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.post.data.entityUtils.PostUtils
import com.jesualex.postVisualizer.post.domain.PostUseCase
import com.jesualex.postVisualizer.utils.UseCaseObserver
import io.objectbox.android.ObjectBoxLiveData

/**
 * Created by jesualex on 19-12-18.
 */

class PostViewModel() : ViewModel(){
    private lateinit var postLiveData : ObjectBoxLiveData<Post>

    fun getPostLiveData() : ObjectBoxLiveData<Post> {
        if(!::postLiveData.isInitialized){
            postLiveData = ObjectBoxLiveData<Post>(PostUtils.getQueryByDate())
        }
        return postLiveData
    }

    fun get() : ObjectBoxLiveData<Post>{
        val postUseCase = PostUseCase()
        postUseCase.execute( object : UseCaseObserver<MutableList<Post>>(){
            override fun onNext(value: MutableList<Post>) {
                super.onNext(value)
                value.removeAll(PostUtils.getPostsDeleted())
                PostUtils.put(value)
            }
        })

        return getPostLiveData()
    }

    fun delete(post : Post){
        post.deleted = true
        PostUtils.put(post)
    }

    fun delete(posts: MutableList<Post>){
        for (post in posts){
            delete(post)
        }
    }
}