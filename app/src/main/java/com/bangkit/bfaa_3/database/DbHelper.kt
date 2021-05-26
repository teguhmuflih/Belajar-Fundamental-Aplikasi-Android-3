package com.bangkit.bfaa_3.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bangkit.bfaa_3.database.DbContract.GitUserColumns.Companion.TABLE_NAME

class DbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbGituser"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_GITUSER = "CREATE TABLE $TABLE_NAME" +
                "(${DbContract.GitUserColumns.USERNAME} TEXT PRIMARY KEY NOT NULL," +
                "${DbContract.GitUserColumns.NAME} TEXT NOT NULL," +
                "${DbContract.GitUserColumns.AVATAR_URL} TEXT NOT NULL," +
                "${DbContract.GitUserColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_GITUSER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}