package com.bangkit.bfaa_3.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.bangkit.bfaa_3.database.DbContract.GitUserColumns.Companion.TABLE_NAME
import com.bangkit.bfaa_3.database.DbContract.GitUserColumns.Companion.USERNAME
import kotlinx.coroutines.InternalCoroutinesApi
import java.sql.SQLException

class UserHelper(context: Context) {

    private var  dbHelper: DbHelper = DbHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: kotlinx.coroutines.internal.synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$USERNAME ASC"
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(id))
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }
}