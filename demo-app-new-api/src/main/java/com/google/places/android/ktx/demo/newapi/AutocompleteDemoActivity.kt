package com.google.places.android.ktx.demo.newapi

import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.widget.PlaceAutocomplete
import com.google.android.libraries.places.widget.PlaceAutocompleteActivity

class AutocompleteDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AutocompleteDemoScreen()
        }
    }

    @Composable
    private fun AutocompleteDemoScreen() {
        var placeDetails by remember { mutableStateOf("No place selected yet") }

        val startAutocomplete =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intent = result.data
                    if (intent != null) {
                        val place = PlaceAutocomplete.getPredictionFromIntent(intent)
                        if (place != null) {
                            placeDetails =
                                "Got place '${place.getFullText(StyleSpan(Typeface.NORMAL))}'"
                        }
                    }
                } else if (result.resultCode == PlaceAutocompleteActivity.RESULT_ERROR) {
                    val intent = result.data
                    if (intent != null) {
                        val status = PlaceAutocomplete.getResultStatusFromIntent(intent)
                        Toast.makeText(
                            this,
                            "Failed to get place '${status?.statusMessage}'",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                val intent = PlaceAutocomplete.createIntent(this@AutocompleteDemoActivity)
                startAutocomplete.launch(intent)
            }) {
                Text("Start Autocomplete")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(placeDetails)
        }
    }
}