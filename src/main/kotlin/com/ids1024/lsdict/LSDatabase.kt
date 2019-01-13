package com.ids1024.lsdict

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private val DB_VERSION = 4

public class LSDatabase(context: Context) : SQLiteAssetHelper(context, "lewis.db", null, DB_VERSION) {
    init {
        setForcedUpgrade()
    }

    public fun search(selection: String?, selectionArgs: Array<String>?, groupBy: String? = null): Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = "dictionary"
        // XXX replace with better method if possible
        val selectionArgs = selectionArgs?.map { s ->
            s.toLowerCase().replace('j', 'i').replace('v', 'u')
        }?.toTypedArray()
        return qb.query(readableDatabase, null, selection, selectionArgs, groupBy, null, null)
    }
}
