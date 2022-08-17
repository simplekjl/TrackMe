package com.simplekjl.trackme.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.simplekjl.trackme.R
import com.simplekjl.trackme.ui.permission.Permissions.hasLocationPermission

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navController = findNavController(R.id.navHostFragment)
        if (hasLocationPermission(this)) {
            navController.navigate(R.id.action_permissionsGranted_move_to_results)
        }
    }
}