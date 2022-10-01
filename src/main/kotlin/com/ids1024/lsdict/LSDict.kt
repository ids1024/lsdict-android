package com.ids1024.lsdict

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Context
import android.app.SearchManager
import android.view.View
import android.content.Intent
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.appcompat.app.AppCompatActivity

import com.ids1024.lsdict.databinding.MainBinding
//import kotlinx.android.synthetic.main.main.recycler_view
//import kotlinx.android.synthetic.main.main.empty_text

class LSDict : AppCompatActivity() {
    private lateinit var db: LSDatabase
    private var search_term: String = ""
    private lateinit var binding: MainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // setContentView(R.layout.main)
        super.onCreate(savedInstanceState)
        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = LSDatabase(this)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

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
        binding.emptyText.visibility = if (result.count != 0) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
        binding.recyclerView.adapter = LSSearchAdapter(result)
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
