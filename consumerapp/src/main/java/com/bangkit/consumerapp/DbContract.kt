package com.bangkit.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DbContract {

    const val AUTHORITY = "com.bangkit.bfaa_3"
    const val SCHEME = "content"

    internal class GitUserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "gituser"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}
