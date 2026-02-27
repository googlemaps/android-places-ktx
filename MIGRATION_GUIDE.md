# Migration Guide - Places Android KTX v3 to v4.0.0

This guide summarizes the changes and steps required to migrate your project from Places Android KTX v3.x to v4.0.0.

## Overview

> [!WARNING]
> **This library is approaching end-of-life.** We plan to icebox the library and archive this repository in the very near term. Users are strongly encouraged to migrate to the official SDK implementations immediately.

Version 4.0.0 is a major release that transitions this library into a **compatibility shim**. Most functionality has been moved to the official [Places SDK for Android](https://developers.google.com/maps/documentation/places/android-sdk/overview) (v3.3.0+).

## Breaking Changes

### 1. KTX Extensions Moved to SDK
The custom KTX extensions previously provided by this library (`await*` functions) are now deprecated in favor of the official SDK implementations.

**Old (v3.x):**
```kotlin
import com.google.android.libraries.places.ktx.api.net.awaitFetchPlace
```

**New (v4.0.0):**
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
    implementation("com.google.maps.android:places-ktx:4.0.0")
}
```

## Demo Application
The demo application has been completely modernized:
- **UI**: Migrated to **Jetpack Compose** and **Material 3**.
- **Architecture**: Implemented **Hilt** and **ViewModel**.
- **SDK**: Upgraded to **Places SDK v5.1.1**.
