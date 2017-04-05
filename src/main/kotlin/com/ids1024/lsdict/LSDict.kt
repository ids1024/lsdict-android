package com.ids1024.lsdict

import android.app.ListActivity
import android.os.Bundle
import android.database.sqlite.SQLiteDatabase
import android.view.Menu
import android.content.Context
import android.app.SearchManager
import android.widget.SearchView
import android.content.Intent

public class LSDict : ListActivity() {
    private lateinit var db: LSDatabase
    private var search_term: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        db = LSDatabase(this)
        handleIntent(intent)

        if (savedInstanceState != null) {
            search(savedInstanceState.getString("search_term"))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("search_term", search_term)
        super.onSaveInstanceState(outState)
    }

    private fun search(word: String) {
        search_term = word
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
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEARCH) {
            search(intent.getStringExtra(SearchManager.QUERY))
        }
    }
}
