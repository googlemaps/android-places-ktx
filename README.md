![Release](https://github.com/googlemaps/android-places-ktx/workflows/Release/badge.svg)
![Stable](https://img.shields.io/badge/stability-stable-green)
[![Tests/Build](https://github.com/googlemaps/android-places-ktx/actions/workflows/test.yml/badge.svg)](https://github.com/googlemaps/android-places-ktx/actions/workflows/test.yml)

![Contributors](https://img.shields.io/github/contributors/googlemaps/android-places-ktx?color=green)
[![License](https://img.shields.io/github/license/googlemaps/android-places-ktx?color=blue)][license]
[![StackOverflow](https://img.shields.io/stackexchange/stackoverflow/t/google-maps?color=orange&label=google-maps&logo=stackoverflow)](https://stackoverflow.com/questions/tagged/google-maps)
[![Discord](https://img.shields.io/discord/676948200904589322?color=6A7EC2&logo=discord&logoColor=ffffff)][Discord server]

# Places Android KTX

## Description
> [!IMPORTANT]
> **Kotlin extensions (KTX) are now natively supported by the [Places SDK for Android][places-sdk] (v3.3.0+).**
>
> This repository is now a compatibility layer that delegates to the official SDK extensions. New projects should prefer using the official SDK extensions directly.

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
    implementation 'com.google.maps.android:places-ktx:3.6.0' // {x-release-please-version}
}
```

## Usage

Accessing API responses made by `PlacesClient` can be retrieved using coroutines via the official Places SDK extensions. 

This library serves as a **compatibility shim** and provides DSL builders for projects migrating from older versions of the Places KTX library. New projects are encouraged to use the official SDK extensions directly.

### Example: Programmatic Autocomplete
The example here demonstrates how you can use the official SDK's KTX extensions alongside with Android’s [ViewModel KTX][viewmodel-ktx]’s `viewModelScope` and `StateFlow`.

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
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.kotlin.awaitFindAutocompletePredictions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(FlowPreview::class)
class PlacesSearchViewModel(private val placesClient: PlacesClient) : ViewModel() {
    private val _query = MutableStateFlow("")
    private var sessionToken: AutocompleteSessionToken? = null

    val results = _query
        .debounce(300)
        .flatMapLatest { query ->
            flow {
                if (sessionToken == null) {
                    sessionToken = AutocompleteSessionToken.newInstance()
                }
                val response = placesClient.awaitFindAutocompletePredictions {
                    this.query = query
                    this.sessionToken = this@PlacesSearchViewModel.sessionToken
                    countries = listOf("US")
                }
                emit(response.autocompletePredictions)
            }
        }
}
```

### Sample App

A [demo](app) application is contained within this repository that illustrates the use of this KTX library.

To run the demo app, create a `secrets.properties` file in your root directory (Note: this file should *NOT* be
under version control to protect your API key) and add your API key:

```properties
PLACES_API_KEY=YOUR_API_KEY
```

Replace `YOUR_API_KEY` with the API key you obtained in the first step. You can also refer to [local.defaults.properties](local.defaults.properties) for an example of the expected format. Then build and run.


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

This library is offered via an open source [license]. It is not governed by the Google Maps Platform Support [Technical Support Services Guidelines], the [SLA], or the [Deprecation Policy]. However, any Google Maps Platform services used by the library remain subject to the Google Maps Platform Terms of Service.

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

