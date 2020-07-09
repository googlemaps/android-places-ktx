package com.google.android.libraries.places.ktx.api.model

import com.google.android.libraries.places.api.model.OpeningHours

/**
 * Builds a new [OpeningHours].
 *
 * @param actions the actions to apply to the [OpeningHours.Builder]
 *
 * @return the constructed [OpeningHours]
 */
inline fun openingHours(actions: OpeningHours.Builder.() -> Unit): OpeningHours =
    OpeningHours.builder()
        .apply(actions)
        .build()