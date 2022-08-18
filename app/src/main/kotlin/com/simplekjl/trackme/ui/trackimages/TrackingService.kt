package com.simplekjl.trackme.ui.trackimages

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.simplekjl.trackme.R
import com.simplekjl.trackme.utils.Constants.ACTION_START_FOREGROUND_SERVICE
import com.simplekjl.trackme.utils.Constants.ACTION_STOP_FOREGROUND_SERVICE
import com.simplekjl.trackme.utils.Constants.LOCATION_FASTEST_UPDATE_INTERVAL
import com.simplekjl.trackme.utils.Constants.LOCATION_UPDATE_INTERVAL
import com.simplekjl.trackme.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.simplekjl.trackme.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.simplekjl.trackme.utils.Constants.NOTIFICATION_ID
import com.simplekjl.trackme.utils.GeneralFuncs.calculateDistance
import com.simplekjl.trackme.utils.GeneralFuncs.formatDistance
import org.koin.android.ext.android.inject

class TrackingService : LifecycleService() {

    private val notificationManager: NotificationManager by inject()

    private val notification: NotificationCompat.Builder =
        NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_mountain_icon)

    private val fusedLocationProviderClient: FusedLocationProviderClient by inject()

    companion object {
        val started = MutableLiveData<Boolean>()
        val locationList = MutableLiveData<MutableList<LatLng>>()
    }


    private fun initValues() {
        started.postValue(false)
        locationList.postValue(mutableListOf())
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            val locations = result.locations
            for (location in locations) {
                updateLocationList(location)
                updateNotificationPeriodically()

            }
        }
    }


    private fun updateLocationList(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        locationList.value?.apply {
            add(latLng)
            locationList.postValue(this)
        }

    }

    private fun updateNotificationPeriodically() {
        locationList.value?.let {
            notification.setContentTitle("Distance Travelled")
                .setContentText(formatDistance(calculateDistance(it)))
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    override fun onCreate() {
        super.onCreate()
        initValues()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                ACTION_START_FOREGROUND_SERVICE -> {
                    started.postValue(true)
                    startForegroundService()
                    startLocationUpdates()
                }
                ACTION_STOP_FOREGROUND_SERVICE -> {
                    started.postValue(false)
                    stopForegroundService()

                }
                else -> {}
            }

        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notification.build())

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            fastestInterval = LOCATION_FASTEST_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()

        )
    }

    private fun stopForegroundService() {
        removeLocationUpdates()
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(
            NOTIFICATION_ID
        )
        stopForeground(true)
        stopSelf()
    }

    private fun removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

}