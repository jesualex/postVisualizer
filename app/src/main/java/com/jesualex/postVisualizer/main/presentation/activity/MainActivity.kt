package com.jesualex.postVisualizer.main.presentation.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jesualex.postVisualizer.post.data.entity.Post
import com.jesualex.postVisualizer.post.presentation.fragment.PostFragment
import com.jesualex.postVisualizer.R
import com.jesualex.postVisualizer.webview.WebviewFragment

class MainActivity : AppCompatActivity(), PostFragment.OnPostClickListener {
    lateinit var webViewFragment: WebviewFragment

    override fun onPostClick(item: Post) {
        if(!::webViewFragment.isInitialized){
            webViewFragment = WebviewFragment()
        }

        if(webViewFragment.isAdded){ return }

        webViewFragment.url = item.url ?: item.story_url

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, webViewFragment)
            .addToBackStack(webViewFragment.tag)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postFragment = PostFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, postFragment)
            .addToBackStack(postFragment.tag)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
