package com.simplekjl.trackme.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.text.DecimalFormat

object GeneralFuncs {

    fun calculateDistance(locationList: MutableList<LatLng>): String {
        if (locationList.size > 1) {
            val distance =
                SphericalUtil.computeDistanceBetween(locationList.first(), locationList.last())
            val formatDistance = DecimalFormat("#.##").format(distance / 1000)
            return formatDistance + "KM"
        }

        return "0.00KM"

    }
}