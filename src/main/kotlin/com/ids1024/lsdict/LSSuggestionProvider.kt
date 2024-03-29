package com.ids1024.lsdict

import android.content.ContentProvider
import android.database.Cursor
import android.net.Uri
import android.content.ContentValues
import android.app.SearchManager
import android.database.CursorWrapper

private class LSCursor(cursor: Cursor) : CursorWrapper(cursor) {
    override fun getColumnIndex(columnName: String): Int {
        return when (columnName) {
            SearchManager.SUGGEST_COLUMN_QUERY ->
                super.getColumnIndex("key")
            SearchManager.SUGGEST_COLUMN_TEXT_1 ->
                super.getColumnIndex("word")
            else ->
                super.getColumnIndex(columnName)
        }
    }
}

public class LSSuggestionProvider : ContentProvider() {
    private lateinit var db: LSDatabase

    override fun onCreate(): Boolean {
        db = LSDatabase(context!!)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor {
        // Use groupBy to avoid same word appearing twice (when it has multiple definitions)
        return LSCursor(db.search(selection, selectionArgs, groupBy="key,word"))
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String {
        return SearchManager.SUGGEST_MIME_TYPE
    }
}
