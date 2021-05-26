package com.bangkit.bfaa_3.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.bangkit.bfaa_3.database.DbContract.AUTHORITY
import com.bangkit.bfaa_3.database.DbContract.GitUserColumns.Companion.CONTENT_URI
import com.bangkit.bfaa_3.database.DbContract.GitUserColumns.Companion.TABLE_NAME
import com.bangkit.bfaa_3.database.UserHelper
import kotlinx.coroutines.InternalCoroutinesApi

class FavProvider : ContentProvider() {
    companion object {
        private const val GITUSER = 1
        private const val GITUSER_USERNAME = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var gitUserHelper: UserHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, GITUSER)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", GITUSER_USERNAME)
        }
    }

    @InternalCoroutinesApi
    override fun onCreate(): Boolean {
        gitUserHelper = UserHelper.getInstance(context as Context)
        gitUserHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor {
        return gitUserHelper.queryAll()
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (GITUSER) {
            sUriMatcher.match(uri) -> gitUserHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when (GITUSER_USERNAME) {
            sUriMatcher.match(uri) -> gitUserHelper.update(uri.lastPathSegment.toString(),values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (GITUSER_USERNAME) {
            sUriMatcher.match(uri) -> gitUserHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}