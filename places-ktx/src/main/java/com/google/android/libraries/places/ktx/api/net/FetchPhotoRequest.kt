package com.google.android.libraries.places.ktx.api.net

import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.net.FetchPhotoRequest

/**
 * Builds a new [FetchPhotoRequest].
 *
 * @param photoMetadata the metadata for the requested photo
 * @param actions the actions to apply to the [FetchPhotoRequest.Builder]
 *
 * @return the constructed [FetchPhotoRequest]
 */
inline fun fetchPhotoRequest(
    photoMetadata: PhotoMetadata,
    noinline actions: (FetchPhotoRequest.Builder.() -> Unit)? = null
): FetchPhotoRequest {
    val request = FetchPhotoRequest.builder(photoMetadata)
    actions?.let { request.apply(it) }
    return request.build()
}
