package com.jesualex.postVisualizer.main.presentation.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.post.presentation.fragment.PostFragment
import com.jesualex.postVisualizer.R

class MainActivity : AppCompatActivity(), PostFragment.OnPostClickListener {
    override fun onPostClick(item: Post) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
