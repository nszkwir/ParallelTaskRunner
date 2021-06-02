package com.spitzer.paralleltaskrunner.taskrunner.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DogFact(
    val fact: String? = ""
): Parcelable