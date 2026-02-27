// Copyright 2026 Google LLC
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

package com.google.places.android.ktx.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.places.android.ktx.demo.ui.DemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesPhotoDemoActivity : ComponentActivity() {

    private val viewModel: PlacesPhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoTheme {
                PlacesPhotoScreen(
                    viewModel = viewModel,
                    onBackPressed = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesPhotoScreen(
    viewModel: PlacesPhotoViewModel,
    onBackPressed: () -> Unit
) {
    val searchEvent by viewModel.searchResults.collectAsStateWithLifecycle()
    val photoState by viewModel.photoState.collectAsStateWithLifecycle()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Places Photo Demo") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Input
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.onSearchQueryChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search for a place with photos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )

            // Results or Photo display
            Box(modifier = Modifier.fillMaxSize()) {
                if (photoState.uri != null || photoState.isLoading || photoState.error != null) {
                    // Show Photo Display
                    PhotoDisplay(
                        state = photoState,
                        onBackPressed = { viewModel.onSearchQueryChanged(searchQuery) }
                    )
                } else {
                    // Show Search Results
                    SearchResultsList(
                        event = searchEvent,
                        onPredictionClick = { viewModel.onPredictionClicked(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultsList(
    event: PhotoDemoEvent,
    onPredictionClick: (AutocompletePrediction) -> Unit
) {
    when (event) {
        is PhotoDemoEventIdle -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Type to find a place", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        is PhotoDemoEventLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is PhotoDemoEventResults -> {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(event.predictions) { prediction ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPredictionClick(prediction) }
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(prediction.getPrimaryText(null).toString(), fontWeight = FontWeight.Bold)
                            Text(prediction.getSecondaryText(null).toString(), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
        is PhotoDemoEventError -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${event.exception.message}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun PhotoDisplay(
    state: PhotoState,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
            Text("Resolving Photo URI...")
        } else if (state.error != null) {
            Text("Error", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.error)
            Text(state.error)
            Button(onClick = onBackPressed, Modifier.padding(top = 16.dp)) {
                Text("Back to Results")
            }
        } else if (state.uri != null) {
            Text("Fetched Photo URI", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = state.uri.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(8.dp)
            )
            
            Spacer(Modifier.height(16.dp))
            
            // This is the core of the demo: Using Coil's AsyncImage with the fetched URI
            AsyncImage(
                model = state.uri,
                contentDescription = "Place Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.LightGray, MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Button(onClick = onBackPressed, Modifier.padding(top = 24.dp)) {
                Text("Search Another Photo")
            }
        }
    }
}
