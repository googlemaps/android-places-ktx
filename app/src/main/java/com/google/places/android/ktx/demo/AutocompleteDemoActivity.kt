package com.google.places.android.ktx.demo

import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.places.android.ktx.demo.ui.DemoTheme

class AutocompleteDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DemoTheme {
                AutocompleteDemoScreen(onBackPressed = { finish() })
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AutocompleteDemoScreen(onBackPressed: () -> Unit) {
        var placeDetails by remember { mutableStateOf<String?>(null) }

        val startAutocomplete =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intent = result.data
                    if (intent != null) {
                        val place = Autocomplete.getPlaceFromIntent(intent)
                        placeDetails = "Got place '${place.displayName}' (${place.formattedAddress})"
                    }
                } else if (result.resultCode == AutocompleteActivity.RESULT_ERROR) {
                    val intent = result.data
                    if (intent != null) {
                        val status = Autocomplete.getStatusFromIntent(intent)
                        Toast.makeText(
                            this,
                            "Failed to get place '${status.statusMessage}'",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Autocomplete Demo") },
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
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = placeDetails ?: "No place selected yet",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = if (placeDetails != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                val fields = listOf(Place.Field.ID, Place.Field.DISPLAY_NAME, Place.Field.FORMATTED_ADDRESS)
                                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                                    .build(this@AutocompleteDemoActivity)
                                startAutocomplete.launch(intent)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text("Start Autocomplete")
                        }
                    }
                }
            }
        }
    }
}