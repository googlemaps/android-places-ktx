package com.google.android.libraries.places.ktx.api.net

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest

/**
 * Builds a new [FindCurrentPlaceRequest].
 *
 * @param placeFields the fields of the places to be returned
 * @param actions the actions to apply to the [FindCurrentPlaceRequest.Builder]
 *
 * @return the constructed [FindCurrentPlaceRequest]
 */
inline fun findCurrentPlaceRequest(
    placeFields: List<Place.Field>,
    noinline actions: (FindCurrentPlaceRequest.Builder.() -> Unit)? = null
): FindCurrentPlaceRequest {
    val request = FindCurrentPlaceRequest.builder(placeFields)
    actions?.let { request.apply(it) }
    return request.build()
}