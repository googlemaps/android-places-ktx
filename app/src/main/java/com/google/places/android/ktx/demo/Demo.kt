package com.google.places.android.ktx.demo

import android.app.Activity
import androidx.annotation.StringRes

enum class Demo(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val activity: Class<out Activity>
) {
    AUTOCOMPLETE_FRAGMENT_DEMO(
        R.string.autocomplete_fragment_demo_title,
        R.string.autocomplete_fragment_demo_description,
        AutocompleteDemoActivity::class.java
    ),
    PLACES_SEARCH_DEMO(
        R.string.places_demo_title,
        R.string.places_demo_description,
        PlacesSearchDemoActivity::class.java
    )
}