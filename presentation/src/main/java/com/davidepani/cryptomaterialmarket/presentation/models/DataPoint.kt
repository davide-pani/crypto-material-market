package com.davidepani.cryptomaterialmarket.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataPoint(
    val x: Double,
    val y: Double,
    val label: String?
) : Parcelable
