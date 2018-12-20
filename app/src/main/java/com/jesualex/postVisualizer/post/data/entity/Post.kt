package com.jesualex.postVisualizer.post.data.entity

import com.jesualex.postVisualizer.utils.Utils
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 * Created by jesualex on 18-12-18.
 */

@Entity
data class Post(
    @Id(assignable = true) var objectID: Long = 0,
    val created_at: Date = Calendar.getInstance().time,
    val title : String? = "",
    val url : String? = "",
    val story_title : String? = "",
    val story_url : String? = "",
    val author : String? = "",
    var deleted : Boolean = false
){
    fun getTime() : String = Utils.getTimeAgo(created_at.time)

    override fun equals(other: Any?): Boolean {
        return other != null && other is Post && other.objectID == objectID
    }
}