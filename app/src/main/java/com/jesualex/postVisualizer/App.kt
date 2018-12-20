package com.jesualex.postVisualizer

import android.app.Application
import com.jesualex.postVisualizer.post.data.entity.MyObjectBox
import io.objectbox.BoxStore

/**
 * Created by jesualex on 19-12-18.
 */

class App() : Application(){

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(this).build();
    }

    companion object {
        lateinit var boxStore : BoxStore
    }
}