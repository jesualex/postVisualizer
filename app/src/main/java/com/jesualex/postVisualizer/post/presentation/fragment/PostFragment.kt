package com.jesualex.postVisualizer.post.presentation.fragment

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jesualex.postVisualizer.*
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.post.data.entityUtils.PostUtils
import com.jesualex.postVisualizer.post.presentation.adapter.PostAdapter
import com.jesualex.postVisualizer.post.presentation.viewModel.PostViewModel

class PostFragment : Fragment() {
    private var columnCount = 1
    private var listener: OnPostClickListener? = null
    private lateinit var adapter : PostAdapter
    private val postViewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = PostAdapter(ArrayList(), listener)

        postViewModel.get().observe(this, Observer { posts ->
            posts?.let { adapter.setList(it) }
        })

        arguments?.let {
            columnCount = it.getInt(argColumnCount)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = if (columnCount <= 1) LinearLayoutManager(context) else GridLayoutManager(context, columnCount)
                adapter = this@PostFragment.adapter
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {

                override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                    if(p0 is PostAdapter.ViewHolder){
                        val post = p0.mView.tag as Post
                        postViewModel.delete(post)
                    }
                }

            }).attachToRecyclerView(view)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPostClickListener) {
            listener = context
        } else {
            val parent = parentFragment

            if(parent is OnPostClickListener){
                listener = parent
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnPostClickListener {
        fun onPostClick(item: Post)
    }

    companion object {
        private const val argColumnCount = "column-count"

        @JvmStatic fun newInstance(columnCount: Int) = PostFragment().apply {
            arguments = Bundle().apply {
                putInt(argColumnCount, columnCount)
            }
        }
    }
}
