package com.jesualex.postVisualizer.post.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jesualex.postVisualizer.*
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.post.domain.PostUseCase
import com.jesualex.postVisualizer.post.presentation.adapter.PostAdapter
import com.jesualex.postVisualizer.utils.UseCaseObserver

class PostFragment : Fragment() {
    private var columnCount = 1
    private var listener: OnPostClickListener? = null
    private lateinit var postUseCase : PostUseCase
    private var adapter : PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postUseCase = PostUseCase()
        postUseCase.execute( object : UseCaseObserver<MutableList<Post>>(){
            override fun onNext(value: MutableList<Post>) {
                super.onNext(value)
                adapter?.setList(value)
            }
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
                val list = MutableList(4){ Post(title = "asjhfbbfahjv ashjgdvs") }
                this@PostFragment.adapter =
                        PostAdapter(list, listener)
                adapter = this@PostFragment.adapter
            }
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
