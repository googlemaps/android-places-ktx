![Release](https://github.com/googlemaps/android-places-ktx/workflows/Release/badge.svg)
![Stable](https://img.shields.io/badge/stability-stable-green)
[![Tests/Build](https://github.com/googlemaps/android-places-ktx/actions/workflows/test.yml/badge.svg)](https://github.com/googlemaps/android-places-ktx/actions/workflows/test.yml)

![Contributors](https://img.shields.io/github/contributors/googlemaps/android-places-ktx?color=green)
[![License](https://img.shields.io/github/license/googlemaps/android-places-ktx?color=blue)][license]
[![StackOverflow](https://img.shields.io/stackexchange/stackoverflow/t/google-maps?color=orange&label=google-maps&logo=stackoverflow)](https://stackoverflow.com/questions/tagged/google-maps)
[![Discord](https://img.shields.io/discord/676948200904589322?color=6A7EC2&logo=discord&logoColor=ffffff)][Discord server]

# Places Android KTX

## Description
This repository contains Kotlin extensions (KTX) for:
1. The [Places SDK for Android][places-sdk]

It enables you to write more concise, idiomatic Kotlin.

## Requirements

- [Sign up with Google Maps Platform]
- A Google Maps Platform [project] with the **Maps SDK for Android** enabled
- An [API key] associated with the project above
- Android API level 24+
- Kotlin-enabled project
- Kotlin coroutines

## Installation

If you are using the Places SDK through Google Play Services:

```groovy
dependencies {
    implementation 'com.google.maps.android:places-ktx:3.5.0'
}
```

## Usage

Accessing API responses made by `PlacesClient` can be retrieved using coroutines vs. using
[Task](https://developers.google.com/android/reference/com/google/android/gms/tasks/Task.html) objects.
The example here demonstrates how you can use this feature alongside with Android’s [ViewModel KTX][viewmodel-ktx]’s `viewModelScope`.
Additionally, you can use a DSL provided by this library to construct requests vs. using builders.

_Before_
```kotlin
val bias = RectangularBounds.newInstance(
    LatLng(22.458744, 88.208162), // SW lat, lng
    LatLng(22.730671, 88.524896) // NE lat, lng
)

// Create a new programmatic Place Autocomplete request in Places SDK for Android using builders
val newRequest = FindAutocompletePredictionsRequest
    .builder()
    .setLocationBias(bias)
    .setTypesFilter(listOf(PlaceTypes.ESTABLISHMENT))
    .setQuery("123 Main Street")
    .setCountries("IN")
    .build()

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
        locationBias = bias
        typesFilter = listOf(PlaceTypes.ESTABLISHMENT)
        query = "123 Main Street"
        countries = listOf("US")
    }

    // Perform autocomplete predictions request
    val response = placesClient.awaitFindAutocompletePredictions(request)
    // Handle response
}
```

### Sample App

A [demo](app) application is contained within this repository that illustrates the use of this KTX library.

To run the demo app, update the `local.properties` file in your root directory called (Note: this file should *NOT* be
under version control to protect your API key) and add a single line to `local.properties` that
looks like `PLACES_API_KEY="YOUR_API_KEY"`, where `YOUR_API_KEY` is the API key you obtained in
the first step. You can also take a look at the [local.defaults.properties](local.defaults.properties)
as an example. Then build and run.


## Documentation

You can learn more about all the extensions provided by this library by reading the [reference documents].

For more information, check out the [detailed guide][places-sdk].

## Contributing

Contributions are welcome and encouraged! If you'd like to contribute, send us a [pull request] and refer to our [code of conduct] and [contributing guide].

## Terms of Service

This library uses Google Maps Platform services. Use of Google Maps Platform services through this library is subject to the Google Maps Platform [Terms of Service].

If your billing address is in the European Economic Area, effective on 8 July 2025, the [Google Maps Platform EEA Terms of Service](https://cloud.google.com/terms/maps-platform/eea) will apply to your use of the Services. Functionality varies by region. [Learn more](https://developers.google.com/maps/comms/eea/faq).

This library is not a Google Maps Platform Core Service. Therefore, the Google Maps Platform Terms of Service (e.g. Technical Support Services, Service Level Agreements, and Deprecation Policy) do not apply to the code in this library.

## Support

This library is offered via an open source [license]. It is not governed by the Google Maps Platform Support [Technical Support Services Guidelines, the SLA, or the [Deprecation Policy]. However, any Google Maps Platform services used by the library remain subject to the Google Maps Platform Terms of Service.

This library adheres to [semantic versioning] to indicate when backwards-incompatible changes are introduced. Accordingly, while the library is in version 0.x, backwards-incompatible changes may be introduced at any time.

If you find a bug, or have a feature request, please [file an issue] on GitHub. If you would like to get answers to technical questions from other Google Maps Platform developers, ask through one of our [developer community channels]. If you'd like to contribute, please check the [contributing guide].

You can also discuss this library on our [Discord server].

[places-sdk]: https://developers.google.com/maps/documentation/places/android-sdk/overview
[viewmodel-ktx]: https://developer.android.com/kotlin/ktx#viewmodel

[API key]: https://developers.google.com/maps/documentation/android-sdk/get-api-key
[documentation]: https://googlemaps.github.io/android-places-ktx

[code of conduct]: ?tab=coc-ov-file#readme
[contributing guide]: CONTRIBUTING.md
[Deprecation Policy]: https://cloud.google.com/maps-platform/terms
[developer community channels]: https://developers.google.com/maps/developer-community
[Discord server]: https://discord.gg/hYsWbmk
[file an issue]: https://github.com/googlemaps/android-places-ktx/issues/new/choose
[license]: LICENSE
[project]: https://developers.google.com/maps/documentation/android-sdk/cloud-setup
[pull request]: https://github.com/googlemaps/android-places-ktx/compare
[semantic versioning]: https://semver.org
[Sign up with Google Maps Platform]: https://console.cloud.google.com/google/maps-apis/start
[similar inquiry]: https://github.com/googlemaps/android-places-compose/issues
[SLA]: https://cloud.google.com/maps-platform/terms/sla
[Technical Support Services Guidelines]: https://cloud.google.com/maps-platform/terms/tssg
[Terms of Service]: https://cloud.google.com/maps-platform/terms

