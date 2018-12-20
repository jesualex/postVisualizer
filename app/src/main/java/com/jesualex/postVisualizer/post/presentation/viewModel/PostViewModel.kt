package com.jesualex.postVisualizer.post.presentation.viewModel

import android.arch.lifecycle.ViewModel
import android.view.View
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.post.data.entityUtils.PostUtils
import com.jesualex.postVisualizer.post.domain.PostUseCase
import com.jesualex.postVisualizer.utils.OnEventListener
import com.jesualex.postVisualizer.utils.UseCaseObserver
import io.objectbox.android.ObjectBoxLiveData

/**
 * Created by jesualex on 19-12-18.
 */

class PostViewModel : ViewModel(){
    private lateinit var postLiveData : ObjectBoxLiveData<Post>
    private val postUseCase = PostUseCase()
    private var refreshListener : OnEventListener<Boolean>? = null

    private fun getUseCaseObserver() = object : UseCaseObserver<MutableList<Post>>(){
        override fun onNext(value: MutableList<Post>) {
            super.onNext(value)
            value.removeAll(PostUtils.getPostsDeleted())
            PostUtils.put(value)
            refreshListener?.OnEvent(true)
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            e.printStackTrace()
            refreshListener?.OnEvent(false)
        }
    }

    fun setRefreshListener(refreshListener: (Boolean) -> Unit){
        this.refreshListener = object : OnEventListener<Boolean> {
            override fun OnEvent(event: Boolean) {
                refreshListener.invoke(event)
            }
        }
    }

    fun getPostLiveData() : ObjectBoxLiveData<Post> {
        if(!::postLiveData.isInitialized){
            postLiveData = ObjectBoxLiveData<Post>(PostUtils.getQueryByDate())
        }
        return postLiveData
    }

    fun getAndUpdate() : ObjectBoxLiveData<Post>{
        postUseCase.execute(getUseCaseObserver())

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

    fun refresh(){
        postUseCase.execute(getUseCaseObserver())
    }
}