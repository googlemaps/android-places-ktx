package com.google.places.android.ktx.demo

import android.os.Bundle
import android.view.Menu
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.ViewAnimator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesSearchDemoActivity : AppCompatActivity() {

    private val viewAnimator: ViewAnimator by lazy {
        findViewById<ViewAnimator>(R.id.view_animator)
    }

    private val progressBar: ProgressBar by lazy {
        findViewById<ProgressBar>(R.id.progress_bar)
    }

    private val adapter = PlacePredictionAdapter()
    private val viewModel: PlacesSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_search)
        setSupportActionBar(findViewById(R.id.toolbar))
        initRecyclerView()
        viewModel.events.observe(this, Observer { event ->
            when(event) {
                is PlacesSearchEventLoading -> {
                    progressBar.isIndeterminate = true
                }
                is PlacesSearchEventError -> {
                    progressBar.isIndeterminate = false
                    viewAnimator.displayedChild = 0
                }
                is PlacesSearchEventFound -> {
                    progressBar.isIndeterminate = false
                    adapter.setPredictions(event.places)
                    viewAnimator.displayedChild = if (event.places.isEmpty()) 0 else 1
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.also {
            it.queryHint = getString(R.string.search_a_place)
            it.isIconifiedByDefault = false
            it.isFocusable = true
            it.isIconified = false
            it.requestFocusFromTouch()
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.onSearchQueryChanged(newText)
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun initRecyclerView() {
        findViewById<RecyclerView>(R.id.recycler_view).also {
            val layoutManager = LinearLayoutManager(this)
            it.layoutManager = layoutManager
            it.adapter = adapter
            it.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        }
    }

}
