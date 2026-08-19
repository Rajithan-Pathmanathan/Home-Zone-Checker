package com.example.home_zone_checker

import android.location.Location

object ZoneConfig {
    const val REFERENCE_LAT = 6.972544
    const val REFERENCE_LNG = 79.914655
    const val RADIUS_METERS = 200f
}

data class ZoneResult(
    val isInside: Boolean,
    val distanceMeters: Float
)

object ZoneChecker {
    fun checkZone(currentLat: Double, currentLng: Double): ZoneResult {
        val referenceLocation = Location("reference").apply {
            latitude = ZoneConfig.REFERENCE_LAT
            longitude = ZoneConfig.REFERENCE_LNG
        }
        val currentLocation = Location("current").apply {
            latitude = currentLat
            longitude = currentLng
        }
        val distance = currentLocation.distanceTo(referenceLocation)
        val inside = distance <= ZoneConfig.RADIUS_METERS
        return ZoneResult(isInside = inside, distanceMeters = distance)
    }
}