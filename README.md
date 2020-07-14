[![Discord](https://img.shields.io/discord/676948200904589322)](https://discord.gg/hYsWbmk)
![Apache-2.0](https://img.shields.io/badge/license-Apache-blue)

Places Android KTX
==================

## Description
This repository contains Kotlin extensions (KTX) for:
1. The [Places SDK for Android][places-sdk]

It enables you to write more concise, idiomatic Kotlin.

## Requirements
* API level 16+

## Installation

If you are using the Places SDK through Google Play Services:

```groovy
dependencies {
    implementation 'com.google.maps.android:places-ktx:0.2.2'
}
```

Alternatively, if you are using the Places standalone library (for use only with Maps v 3.1.0 beta):

```groovy {
    implementation 'com.google.maps.android:places-v3-ktx:0.2.2'
}

## Example Usage

Accessing API responses made by `PlacesClient` can be retrieved using coroutines vs. using
[Task](https://developers.google.com/android/reference/com/google/android/gms/tasks/Task.html) objects.
The example here demonstrates how you can use this feature alongside with Android’s [ViewModel KTX](viewmodel-ktx)’s `viewModelScope`.
Additionally, you can use a DSL provided by this library to construct requests vs. using builders.

_Before_
```kotlin
    val bias = RectangularBounds.newInstance(
        LatLng(22.458744, 88.208162), // SW lat, lng
        LatLng(22.730671, 88.524896) // NE lat, lng
    );

    // Create a new programmatic Place Autocomplete request in Places SDK for Android using builders
    val newRequest = FindAutocompletePredictionsRequest
        .builder()
        .setLocationBias(bias)
        .setTypeFilter(TypeFilter.ESTABLISHMENT)
        .setQuery("123 Main Street")
        .setCountries("IN")
        .build();

    // Perform autocomplete predictions request
    placesClient.findAutocompletePredictions(newRequest).addOnSuccessListener { response ->
        // Handle response
    }.addOnFailureListener { exception ->
        // Handle exception
    }
```

_After_
```kotlin
val handler = CoroutineExceptionHandler { _, exception ->
    // Handle exception
}

viewModelScope.launch(handler) {
    val bias: LocationBias = RectangularBounds.newInstance(
        LatLng(37.7576948, -122.4727051), // SW lat, lng
        LatLng(37.808300, -122.391338) // NE lat, lng
    )

    // Create a new programmatic Place Autocomplete request in Places SDK for Android using DSL
    val request = findAutocompletePredictionsRequest {
        setLocationBias(bias)
        setTypeFilter(TypeFilter.ESTABLISHMENT)
        setQuery("123 Main Street")
        setCountries("US")
    }

    // Perform autocomplete predictions request
    val response = placesClient.awaitFindAutocompletePredictions(request)
    // Handle response
}
```

### Demo App

A [demo](app) application is contained within this repository that illustrates the use of this KTX library.

To run the demo app, you'll have to:

1. [Get a Places API key](api-key)
2. Create a file in the root directory called `secure.properties` (this file should *NOT* be under version control to protect your API key)
3. Add a single line to `secure.properties` that looks like `PLACES_API_KEY="YOUR_API_KEY"`, where `YOUR_API_KEY` is the API key you obtained in the first step. You can also take a look at the [secure.properties.template](secure.properties.template) as an example.
4. Build and run

## Support

Encounter an issue while using this library?

If you find a bug or have a feature request, please [file an issue].
Or, if you'd like to contribute, send us a [pull request] and refer to our [code of conduct].

You can also reach us on our [Discord channel].

For more information, check out the detailed guide on the
[Google Developers site][places-sdk].

[api-key]: https://developers.google.com/places/android-sdk/get-api-key
[Discord channel]: https://discord.gg/hYsWbmk
[code of conduct]: CODE_OF_CONDUCT.md
[file an issue]: https://github.com/googlemaps/android-places-ktx/issues/new/choose
[maps-v3-sdk]: https://developers.google.com/maps/documentation/android-sdk/v3-client-migration
[places-sdk]: https://developers.google.com/places/android-sdk/intro
[pull request]: https://github.com/googlemaps/android-places-ktx/compare
[viewmodel-ktx]: https://developer.android.com/kotlin/ktx#viewmodel
