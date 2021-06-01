package com.spitzer.paralleltaskrunner.taskrunner.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataOne(
    val id: Int,
    val name: String? = ""
): Parcelable