# Migration Guide - Places Android KTX v3 to v4.x.x

This guide summarizes the changes and steps required to migrate your project from Places Android KTX v3.x to v4.0.0.

## Overview

> [!WARNING]
> **This library is approaching end-of-life.** We plan to icebox the library and archive this repository in the very near term. Users are strongly encouraged to migrate to the official SDK implementations immediately.

Version 4.0.0 is a major release that transitions this library into a **compatibility shim**. Most functionality has been moved to the official [Places SDK for Android](https://developers.google.com/maps/documentation/places/android-sdk/overview) (v3.3.0+).

While this library is now primarily a compatibility shim, it still provides a few minor Kotlin-specific conveniences not yet present in the core SDK:

1.  **DSL Builders**: Lightweight Kotlin DSL builders for models (e.g., `place { ... }`, `photoMetadata { ... }`).
2.  **PriceLevel Enum**: A type-safe `PriceLevel` enum for `SearchByTextRequest` filtering.
3.  **SearchNearby Support**: Helper for `searchNearbyPlaceRequest` with a simplified API.

## Breaking Changes

### 1. KTX Extensions Moved to SDK
The custom KTX extensions previously provided by this library (`await*` functions) are now deprecated in favor of the official SDK implementations.

**Old (v3.x):**
```kotlin
import com.google.android.libraries.places.ktx.api.net.awaitFetchPlace
```

**New (v4.0.0+):**
```kotlin
import com.google.android.libraries.places.api.net.kotlin.awaitFetchPlace
```

> [!NOTE]
> The extension functions in this library now delegate to the official SDK versions. You should update your imports to use the SDK versions directly.

### 2. Removal of Legacy Infrastructure
The following legacy components have been **removed**.

#### **AutocompleteSupportFragment Extensions**
The entire `AutocompleteSupportFragment.kt` file has been deleted.
- **Removed**: `AutocompleteSupportFragment.placeSelectionEvents()`
- **Removed**: `PlaceSelectionResult`, `PlaceSelectionSuccess`, and `PlaceSelectionError` classes.
- **Migration**: Use the official SDK's `AutocompleteSupportFragment` or migrate to the Compose-based patterns shown in the demo app.

#### **Legacy Request Wrappers**
- **Removed**: `fetchPhotoRequest(...)` (associated with the removed `FetchPhoto` API).
- **Removed**: `findCurrentPlaceRequest(...)` (associated with the removed `FindCurrentPlace` API).

#### **Redundant Suspending Functions**
- **Removed**: `PlacesClient.awaitSearchNearbyPlace(...)`. 
- **Migration**: Use `PlacesClient.awaitSearchNearby(...)` instead.

### 3. API Relocations (Renamed Files)
Some APIs were moved to new files to better match modern SDK naming conventions.

| Old File | New File | API Maintained |
| :--- | :--- | :--- |
| `FetchPhotoRequest.kt` | `FetchResolvedPhotoUriRequest.kt` | `fetchResolvedPhotoUriRequest` |
| `FindCurrentPlaceRequest.kt` | `SearchNearbyRequest.kt` | `searchNearbyPlaceRequest` |

## Migration Examples (1-to-1)

### FindCurrentPlace to SearchNearby
The `FindCurrentPlace` API has been removed in favor of `SearchNearby`.

**Old (v3.x):**
```kotlin
val response = placesClient.findCurrentPlace(
    findCurrentPlaceRequest(listOf(Place.Field.ID, Place.Field.NAME))
).await()
```

**New (v4.0.0+):**
```kotlin
val locationRestriction = CircularBounds.newInstance(latLng, 1000.0)
val response = placesClient.awaitSearchNearby(
    locationRestriction,
    listOf(Place.Field.ID, Place.Field.NAME)
)
```

### FetchPhoto to FetchResolvedPhotoUri
The `FetchPhoto` API (returning a `Bitmap`) is replaced by `FetchResolvedPhotoUri` (returning a `Uri`).

**Old (v3.x):**
```kotlin
val response = placesClient.fetchPhoto(fetchPhotoRequest(metadata)).await()
val bitmap = response.bitmap
```

**New (v4.0.0+):**
```kotlin
val response = placesClient.awaitFetchResolvedPhotoUri(metadata)
val uri = response.uri
// Use a library like Coil or Glide to load the URI into an ImageView/Compose
// See PlacesPhotoDemoActivity in the app for a complete example.
```

## Technical Notes

### Behavioral Parity & Capabilities
- **FetchResolvedPhotoUri**: Unlike `FetchPhoto`, this API returns a `Uri` instead of a `Bitmap`. This is more efficient for modern Android apps using image loading libraries (Coil, Glide, etc.).
- **Cancellation Safety**: All `await*` functions in this library now delegate directly to the official SDK's native `suspend` functions. These are built to handle coroutine cancellation correctly, ensuring that associated SDK tasks are cancelled and resources are released if the calling coroutine is cancelled.

## Summary of Deprecated APIs

The following APIs are now deprecated shims that delegate to the official SDK:

| File | API |
| :--- | :--- |
| **`PlacesClient.kt`** | `awaitFetchPlace`, `awaitFetchResolvedPhotoUri`, `awaitFindAutocompletePredictions`, `awaitSearchNearby`, `awaitIsOpen`, `awaitSearchByText` |
| **DSL Builders** | `fetchPlaceRequest`, `findAutocompletePredictionsRequest`, `isOpenRequest`, `searchByTextRequest` |

## Dependency Update
Update your `build.gradle` to reference the new version:

```kotlin
dependencies {
    implementation("com.google.maps.android:places-ktx:4.0.0") // {x-release-please-version}
}
```

## Demo Application
The demo application has been completely modernized:
- **UI**: Migrated to **Jetpack Compose** and **Material 3**.
- **Architecture**: Implemented **Hilt** and **ViewModel**.
- **SDK**: Upgraded to **Places SDK v5.1.1**.

Refer to the [app](app) directory for reference implementations using modern Android practices.
