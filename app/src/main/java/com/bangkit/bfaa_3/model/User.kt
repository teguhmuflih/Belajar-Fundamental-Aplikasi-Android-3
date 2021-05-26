package com.bangkit.bfaa_3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var username: String? = null,
    var avatar: String? = null,
    var name: String? = null
): Parcelable