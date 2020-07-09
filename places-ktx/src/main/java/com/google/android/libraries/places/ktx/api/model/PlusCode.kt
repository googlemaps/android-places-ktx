package com.google.android.libraries.places.ktx.api.model

import com.google.android.libraries.places.api.model.PlusCode

/**
 * Builds a new [PlusCode].
 *
 * @param actions the actions to apply to the [PlusCode.Builder]
 *
 * @return the constructed [PlusCode]
 */
inline fun plusCode(actions: PlusCode.Builder.() -> Unit): PlusCode =
    PlusCode.builder()
        .apply(actions)
        .build()