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
inline fun autocompletePrediction(
    placeId: String,
    noinline actions: (AutocompletePrediction.Builder.() -> Unit)? = null
): AutocompletePrediction {
    val builder = AutocompletePrediction.builder(placeId)
    actions?.let { builder.apply(it) }
    return builder.build()
}
