package com.ids1024.lsdict

import android.app.ListActivity
import android.os.Bundle
import android.text.Html
import android.database.sqlite.SQLiteDatabase
import android.widget.SimpleCursorAdapter

public class LSDict : ListActivity {
    private lateinit var db: LSDatabase
    constructor() : super()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
	db = LSDatabase(this)
        val result = db.search("caesar")
        
        val adapter = SimpleCursorAdapter(this, 
                        R.layout.result,
                        result,
                        Array(1, {"description"}),
                        intArrayOf(R.id.result))
        getListView().setAdapter(adapter)
    }
}
