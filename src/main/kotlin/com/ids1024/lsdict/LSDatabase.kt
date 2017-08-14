package com.ids1024.lsdict

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

val DB_VERSION = 1

public class LSDatabase(context: Context) : SQLiteAssetHelper(context, "lewis.db", null, DB_VERSION) {
    public fun search(selection: String?, selectionArgs: Array<String>?) : Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = "dictionary"
        // XXX replace with better method if possible
        val selectionArgs = selectionArgs?.map(fun(s) = s.toLowerCase()
                                                         .replace('j', 'i')
                                                         .replace('v', 'u'))?.toTypedArray()
        return qb.query(readableDatabase, null, selection, selectionArgs, null, null, null)
    }
}
