package com.google.places.android.ktx.demo

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Places.initialize(this, BuildConfig.PLACES_API_KEY)
    }
}