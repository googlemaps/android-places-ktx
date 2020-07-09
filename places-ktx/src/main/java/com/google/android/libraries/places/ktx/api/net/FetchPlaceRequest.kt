package com.google.android.libraries.places.ktx.api.net

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest

/**
 * Builds a new [FetchPlaceRequest].
 *
 * @param placeId the ID of the place to fetch
 * @param placeFields the fields of the place to be requested
 * @param actions the actions to apply to the [FetchPlaceRequest.Builder]
 *
 * @return the constructed [FetchPlaceRequest]
 */
inline fun fetchPlaceRequest(
    placeId: String,
    placeFields: List<Place.Field>,
    noinline actions: (FetchPlaceRequest.Builder.() -> Unit)? = null
): FetchPlaceRequest {
    val request = FetchPlaceRequest.builder(placeId, placeFields)
    actions?.let { request.apply(it) }
    return request.build()
}