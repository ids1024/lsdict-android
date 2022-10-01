package com.ids1024.lsdict

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Context
import android.app.SearchManager
import android.view.View
import android.content.Intent
import android.support.v7.widget.SearchView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.main.recycler_view
import kotlinx.android.synthetic.main.main.empty_text

class LSDict : AppCompatActivity() {
    private lateinit var db: LSDatabase
    private var search_term: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        db = LSDatabase(this)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))

        handleIntent(intent)

        if (savedInstanceState != null) {
            search(savedInstanceState.getString("search_term")!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("search_term", search_term)
        super.onSaveInstanceState(outState)
    }

    private fun search(word: String) {
        search_term = word
        val result = db.search("key=?", arrayOf(word))
        empty_text.visibility = if (result.count != 0) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
        recycler_view.adapter = LSSearchAdapter(result)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                startActivity(Intent(this, LSAbout::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEARCH) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (query != null) {
                search(query)
            }
        }
    }
}
