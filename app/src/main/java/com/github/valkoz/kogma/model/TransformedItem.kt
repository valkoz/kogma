package com.github.valkoz.kogma.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransformedItem(
        val title: String,
        val description: String,
        val pubDate: String,
        val creator: String,
        val categories: String
): Parcelable