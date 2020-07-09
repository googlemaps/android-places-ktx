package com.google.android.libraries.places.ktx.api.model

import com.google.android.libraries.places.api.model.Period

/**
 * Builds a new [Period].
 *
 * @param actions the actions to apply to the [Period.Builder]
 *
 * @return the constructed [Period]
 */
inline fun period(actions: Period.Builder.() -> Unit): Period =
    Period.builder()
        .apply(actions)
        .build()