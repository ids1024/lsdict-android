package com.ids1024.lsdict

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

val DB_VERSION: Int = 1

public class LSDatabase : SQLiteAssetHelper {
    constructor(context: Context) : super(context, "lewis.db", null, DB_VERSION)
    public fun search(word: String) : Cursor {
        val db = getReadableDatabase()
        val qb = SQLiteQueryBuilder()
        qb.setTables("dictionary")
        val c = qb.query(db, null, null, null, null, null, null)
        c.moveToFirst()
        return c
    }
}
