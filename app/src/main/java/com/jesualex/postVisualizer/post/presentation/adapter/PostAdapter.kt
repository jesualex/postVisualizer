package com.jesualex.postVisualizer.post.presentation.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jesualex.postVisualizer.BR
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.R

import com.jesualex.postVisualizer.post.presentation.fragment.PostFragment.OnPostClickListener
import com.jesualex.postVisualizer.databinding.FragmentPostBinding


class PostAdapter(
    private var posts: MutableList<Post>,
    private val listener: OnPostClickListener? = null
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Post
            listener?.onPostClick(item)
        }
    }

    fun setList(posts : MutableList<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = posts[position]
        val binder : FragmentPostBinding? = DataBindingUtil.bind(holder.mView)

        binder?.let { binder.setVariable(BR.post, item) }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = posts.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: View = mView
    }
}
