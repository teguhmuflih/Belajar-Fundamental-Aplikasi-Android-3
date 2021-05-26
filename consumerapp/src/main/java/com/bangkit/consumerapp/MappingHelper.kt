package com.bangkit.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursortoArrayList(cursor: Cursor?): ArrayList<User> {
        val list = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DbContract.GitUserColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DbContract.GitUserColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DbContract.GitUserColumns.AVATAR_URL))

                list.add(
                    User(
                        username, avatar, name
                    )
                )
            }
        }
        return list
    }
}