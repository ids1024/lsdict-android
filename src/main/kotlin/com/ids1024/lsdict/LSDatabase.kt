package com.ids1024.lsdict

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

val DB_VERSION: Int = 1

public class LSDatabase : SQLiteAssetHelper {
    constructor(context: Context?) : super(context, "lewis.db", null, DB_VERSION)
}
