package com.ids1024.lsdict

import android.app.ListActivity
import android.os.Bundle
import android.database.sqlite.SQLiteDatabase
import android.widget.SimpleCursorAdapter
import android.view.Menu
import android.content.Context
import android.app.SearchManager
import android.widget.SearchView
import android.content.Intent

public class LSDict : ListActivity() {
    private lateinit var db: LSDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        db = LSDatabase(this)
        handleIntent(intent)
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
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }
    override protected fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }
    private fun handleIntent(intent: Intent) {
        //if (intent.action == Intent.ACTION_SEARCH) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            search(intent.getStringExtra(SearchManager.QUERY))
        }
    }
}
