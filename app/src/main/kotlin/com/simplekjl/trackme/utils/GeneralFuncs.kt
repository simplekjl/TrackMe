package com.simplekjl.trackme.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.text.DecimalFormat

object GeneralFuncs {

    fun calculateDistance(locationList: MutableList<LatLng>): Double {
        if (locationList.size > 1) {
            return SphericalUtil.computeDistanceBetween(locationList.first(), locationList.last())
        }
        return 0.00
    }

    fun formatDistance(distance: Double): String = DecimalFormat("#.##").format(distance / 1000)

}