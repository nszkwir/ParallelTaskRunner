package com.spitzer.paralleltaskrunner.taskrunner.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTwo(
    val id: Int,
    val name: String? = ""
): Parcelable