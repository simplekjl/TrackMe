package com.simplekjl.trackme.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.simplekjl.domain.usecases.GetImageUrlByLocationUseCase
import com.simplekjl.domain.usecases.LocationDetails
import com.simplekjl.domain.utils.Result.Error
import com.simplekjl.domain.utils.Result.Success
import com.simplekjl.trackme.R
import com.simplekjl.trackme.databinding.ActivityHomeBinding
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityHomeBinding
    private val useCase: GetImageUrlByLocationUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        runBlocking {
            val result = useCase(LocationDetails(-34.0, 151.0))
            when (result) {
                is Error -> {
                    println("meh")
                }
                is Success -> {
                    println(result.data.photoResponse.photo.first())
                }
            }
        }
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}