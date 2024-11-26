package com.example.taskschedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class tasks(
    var taskName: String,
    var taskDate: String,
    var taskCategory: String,
    var taskDescription: String,
    var status: Boolean,
    var visibility: Boolean
) : Parcelable
