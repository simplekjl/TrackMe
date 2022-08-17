package com.simplekjl.trackme.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.text.DecimalFormat

object GeneralFuncs {
    fun calculateElapsedTime(startTime: Long, stopTime: Long): String {
        val elapsedTime = stopTime - startTime
        val seconds = (elapsedTime / 1000) % 60
        val minutes = (elapsedTime / (1000 * 60)) % 60
        val hours = (elapsedTime / (1000 * 60 * 60)) % 24
        return "$hours:$minutes:$seconds"
    }

    fun calculateDistance(locationList: MutableList<LatLng>): String {

        if (locationList.size > 1) {
            val distance =
                SphericalUtil.computeDistanceBetween(locationList.first(), locationList.last())
            return DecimalFormat("#.##").format(distance / 1000)
        }

        return "0.00"

    }
}