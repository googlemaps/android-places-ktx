package com.example.new_places_client

import com.google.common.truth.Truth.assertThat
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PlaceMapperTest {
  @Test
  fun placeMapper_CorrectPlace_ReturnsPlaceDetails() {
    assertThat(
      Place.builder().apply {
        name = "test place"
        id = "test_place_id"
        address = "test place address"
        latLng = LatLng(39.982148, -105.716293)
      }.build().toPlaceDetails()
    ).isEqualTo(
      PlaceDetails(
        placeId = "test_place_id",
        name = "test place",
        location = LatLng(39.982148, -105.716293),
        address = "test place address"
      )
    )
  }

  @Test
  fun placeMapper_MissingAddress_ReturnsPlaceDetails() {
    assertThat(
      Place.builder().apply {
        name = "test place"
        id = "test_place_id"
        latLng = LatLng(39.982148, -105.716293)
      }.build().toPlaceDetails()
    ).isEqualTo(
      PlaceDetails(
        placeId = "test_place_id",
        name = "test place",
        location = LatLng(39.982148, -105.716293)
      )
    )
  }

  @Test
  fun placeMapper_MissingRequiredField_ReturnsNull() {
    assertThat(
      Place.builder().apply {
        id = "test_place_id"
        latLng = LatLng(39.982148, -105.716293)
      }.build().toPlaceDetails()
    ).isNull()

    assertThat(
      Place.builder().apply {
        name = "test place"
        latLng = LatLng(39.982148, -105.716293)
      }.build().toPlaceDetails()
    ).isNull()

    assertThat(
      Place.builder().apply {
        id = "test_place_id"
        name = "test place"
      }.build().toPlaceDetails()
    ).isNull()
  }
}
