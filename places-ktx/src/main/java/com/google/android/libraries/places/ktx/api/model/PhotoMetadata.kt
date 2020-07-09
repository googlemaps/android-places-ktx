package com.google.android.libraries.places.ktx.api.model

import com.google.android.libraries.places.api.model.PhotoMetadata

/**
 * Builds a new [PhotoMetadata].
 *
 * @param photoReference the reference identifying the underlying photo
 * @param actions the actions to apply to the [PhotoMetadata.Builder]
 *
 * @return the constructed [PhotoMetadata]
 */
inline fun photoMetadata(
    photoReference: String,
    noinline actions: (PhotoMetadata.Builder.() -> Unit)? = null
): PhotoMetadata {
    val builder = PhotoMetadata.builder(photoReference)
    actions?.let { builder.apply(it) }
    return builder.build()
}
