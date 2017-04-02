package com.ids1024.lsdict

import android.app.ListActivity
import android.os.Bundle
import android.database.sqlite.SQLiteDatabase
import android.widget.SimpleCursorAdapter
import android.view.Menu

public class LSDict : ListActivity() {
    private lateinit var db: LSDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
	db = LSDatabase(this)
        search("a")
    }
    private fun search(word: String) {
        val term = word.toLowerCase()
                       .replace('j', 'i')
                       .replace('v', 'u')
        val result = db.search(term)
        listView.adapter = LSCursorAdapter(this, result)
    }
    override public fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
