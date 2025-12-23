// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.places.android.ktx.demo.newapi

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.ViewAnimator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesSearchDemoActivity : AppCompatActivity() {

    private val viewAnimator: ViewAnimator by lazy {
        findViewById(R.id.view_animator)
    }

    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progress_bar)
    }

    private val adapter = PlaceResultAdapter()
    private val viewModel: PlacesSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_search)
        setSupportActionBar(findViewById(R.id.toolbar))
        initRecyclerView()
        adapter.onPlaceClickListener = { place ->
            viewModel.onPlaceClicked(place)
        }
        viewModel.events.observe(this) { event ->
            when (event) {
                is PlacesSearchEventLoading -> {
                    progressBar.isIndeterminate = true
                }
                is PlacesSearchEventError -> {
                    progressBar.isIndeterminate = false
                    viewAnimator.displayedChild = 0
                }
                is PlacesSearchEventFound -> {
                    progressBar.isIndeterminate = false
                    adapter.submitList(event.places)
                    viewAnimator.displayedChild = if (event.places.isEmpty()) 0 else 1
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            queryHint = getString(R.string.search_a_place)
            isIconifiedByDefault = false
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search_nearby) {
            viewModel.onSearchNearby()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = linearLayoutManager
            adapter = this@PlacesSearchDemoActivity.adapter
            addItemDecoration(
                DividerItemDecoration(
                    this@PlacesSearchDemoActivity,
                    linearLayoutManager.orientation
                )
            )
        }
    }

}
