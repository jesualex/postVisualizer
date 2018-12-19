package com.jesualex.postVisualizer.utils

import java.util.*

/**
 * Created by jesualex on 19-12-18.
 */
object Utils {
    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS


    fun getTimeAgo(time: Long): String {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }

        val now = Calendar.getInstance().timeInMillis
        if (time > now || time <= 0) {
            return ""
        }

        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            (diff / MINUTE_MILLIS).toString() + " m"
        } else if (diff < 24 * HOUR_MILLIS) {
            (diff / HOUR_MILLIS).toString() + " h"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else {
            (diff / DAY_MILLIS).toString() + " days ago"
        }
    }
}
