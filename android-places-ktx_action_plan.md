# Action Plan: android-places-ktx

## Context
- **Repository:** googlemaps/android-places-ktx
- **Health Score:** 0/100
- **Status:** Action Required

## Health Reasoning
- -5: 5 Stale PRs
- -1: 1 Stale Issues
- -5: 1 outdated dependencies
- -20: Zombie State (0 meaningful commits/90d)
- -5: Low Traffic (<= 100 views/14d)
- -60: 30 GMP Deprecations (CRITICAL)
- -12: 6 other deprecations
- -20: 22 Lint Warnings

## Outdated Dependencies
- Update `com.google.android.gms:play-services-maps` from `19.2.0` to `20.0.0`

## Critical GMP Deprecations
The following deprecated APIs MUST be replaced:

- **'class FetchPhotoRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FetchPhotoRequest.kt:18`
- **'class FetchPhotoRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FetchPhotoRequest.kt:39`
- **'fun fetchPhotoRequest(photoMetadata: PhotoMetadata, actions: (FetchPhotoRequest.Builder.() -> Unit)? = ...): FetchPhotoRequest' is deprecated. Replaced with new API.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FetchPhotoRequest.kt:40`
- **'class FindCurrentPlaceRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FindCurrentPlaceRequest.kt:19`
- **'class FindCurrentPlaceRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FindCurrentPlaceRequest.kt:40`
- **'fun findCurrentPlaceRequest(placeFields: List<Place.Field>, actions: (FindCurrentPlaceRequest.Builder.() -> Unit)? = ...): FindCurrentPlaceRequest' is deprecated. Replaced with new API.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FindCurrentPlaceRequest.kt:41`
- **'class FetchPhotoRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:25`
- **'class FetchPhotoResponse : Any' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:26`
- **'class FindCurrentPlaceRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:33`
- **'class FindCurrentPlaceResponse : Any' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:34`
- **'class FetchPhotoResponse : Any' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:66`
- **'suspend fun PlacesClient.awaitFetchPhoto(photoMetadata: PhotoMetadata, actions: FetchPhotoRequest.Builder.() -> Unit = ...): FetchPhotoResponse' is deprecated. Replaced with new API.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:67`
- **'class FindCurrentPlaceResponse : Any' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:136`
- **'suspend fun PlacesClient.awaitFindCurrentPlace(placeFields: List<Place.Field>): FindCurrentPlaceResponse' is deprecated. Replaced with new API.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:137`
- **'class FetchPhotoRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FetchPhotoRequest.kt:18`
- **'class FetchPhotoRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FetchPhotoRequest.kt:39`
- **'fun fetchPhotoRequest(photoMetadata: PhotoMetadata, actions: (FetchPhotoRequest.Builder.() -> Unit)? = ...): FetchPhotoRequest' is deprecated. Replaced with new API.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FetchPhotoRequest.kt:40`
- **'class FindCurrentPlaceRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FindCurrentPlaceRequest.kt:19`
- **'class FindCurrentPlaceRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FindCurrentPlaceRequest.kt:40`
- **'fun findCurrentPlaceRequest(placeFields: List<Place.Field>, actions: (FindCurrentPlaceRequest.Builder.() -> Unit)? = ...): FindCurrentPlaceRequest' is deprecated. Replaced with new API.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/FindCurrentPlaceRequest.kt:41`
- **'class FetchPhotoRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:25`
- **'class FetchPhotoResponse : Any' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:26`
- **'class FindCurrentPlaceRequest : Any, zzmq' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:33`
- **'class FindCurrentPlaceResponse : Any' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:34`
- **'class FetchPhotoResponse : Any' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:66`
- **'suspend fun PlacesClient.awaitFetchPhoto(photoMetadata: PhotoMetadata, actions: FetchPhotoRequest.Builder.() -> Unit = ...): FetchPhotoResponse' is deprecated. Replaced with new API.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:67`
- **'class FindCurrentPlaceResponse : Any' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:136`
- **'suspend fun PlacesClient.awaitFindCurrentPlace(placeFields: List<Place.Field>): FindCurrentPlaceResponse' is deprecated. Replaced with new API.**
  - Source: Build
  - Location: `places-ktx/src/main/java/com/google/android/libraries/places/ktx/api/net/PlacesClient.kt:137`
- **'enum class AutocompleteActivityMode : Enum<AutocompleteActivityMode!>, Parcelable' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `app/src/main/java/com/google/places/android/ktx/demo/AutocompleteDemoActivity.kt:40`
- **'enum class AutocompleteActivityMode : Enum<AutocompleteActivityMode!>, Parcelable' is deprecated. Deprecated in Java.**
  - Source: Build
  - Location: `app/src/main/java/com/google/places/android/ktx/demo/AutocompleteDemoActivity.kt:40`

## Android Lint Issues
- **UnusedResources:** 10 instances
- **NewerVersionAvailable:** 5 instances
- **GradleDependency:** 4 instances
- **RedundantLabel:** 1 instances
- **AndroidGradlePluginVersion:** 1 instances
- **ObsoleteSdkInt:** 1 instances

## Gemini Prompt Instructions
1. Review the health report and identified issues.
2. Focus on fixing GMP deprecations first as they are critical.
3. Update dependencies to their latest stable versions.
4. Resolve Android Lint warnings to improve codebase quality.
5. Create a detailed implementation plan before starting fixes.
