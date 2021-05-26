package com.bangkit.consumerapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var username: String? = null,
    var avatar: String? = null,
    var name: String? = null
): Parcelable