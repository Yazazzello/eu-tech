package nl.yaz.eurailtech.util

import android.location.Location
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

object LocationHelper {
    fun getLocationInLatLngRad(radiusInMeters: Double, currentLocation: Location): Location {
        val x0: Double = currentLocation.longitude
        val y0: Double = currentLocation.latitude
        // Convert radius from meters to degrees.
        val radiusInDegrees = radiusInMeters / 111320f
        // Get a random distance and a random angle.
        val u: Double = Random.nextDouble()
        val v: Double = Random.nextDouble()
        val w = radiusInDegrees * sqrt(u)
        val t = 2 * Math.PI * v
        // Get the x and y delta values.
        val x = w * cos(t)
        val y = w * sin(t)
        // Compensate the x value.
        val newX = x / cos(Math.toRadians(y0))
        val foundLatitude: Double
        val foundLongitude: Double
        foundLatitude = y0 + y
        foundLongitude = x0 + newX
        val copy = Location(currentLocation)
        copy.latitude = foundLatitude
        copy.longitude = foundLongitude
        return copy
    }
}