package com.google.android.libraries.places.ktx.api.model

import com.google.android.libraries.places.api.model.AutocompletePrediction

/**
 * Builds a new [AutocompletePrediction].
 *
 * @param placeId the place's ID
 * @param actions the actions to apply to the [AutocompletePrediction.Builder]
 *
 * @return the constructed [AutocompletePrediction]
 */
public fun autocompletePrediction(
    placeId: String,
    actions: (AutocompletePrediction.Builder.() -> Unit)? = null
): AutocompletePrediction {
    return AutocompletePrediction.builder(placeId).also { builder ->
        actions?.let { builder.apply(it) }
    }.build()
    // This is a new function.
}
