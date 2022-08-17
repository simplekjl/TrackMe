package com.simplekjl.trackme.ui.trackimages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.simplekjl.trackme.R
import com.simplekjl.trackme.databinding.FragmentResultsBinding
import com.simplekjl.trackme.ui.permission.Permissions.hasBackgroundLocationPermission
import com.simplekjl.trackme.ui.permission.Permissions.requestBackgroundLocationPermission
import com.simplekjl.trackme.utils.Constants.ACTION_START_FOREGROUND_SERVICE
import com.simplekjl.trackme.utils.Constants.ACTION_STOP_FOREGROUND_SERVICE
import com.simplekjl.trackme.utils.GeneralFuncs.calculateDistance
import com.simplekjl.trackme.utils.GeneralFuncs.formatDistance
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import kotlinx.android.synthetic.main.fragment_results.view.startBtn
import org.koin.androidx.viewmodel.ext.android.viewModel


class TrackingFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private val trackingViewModel by viewModel<TrackingViewModel>()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationList = mutableListOf<LatLng>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.distanceValue.text = getString(R.string.initial_distance, "0.0")
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.toolbar.startBtn.setOnClickListener {
            updateMenuAndStartTracking()
        }
    }

    private fun setObservers() {
        TrackingService.locationList.observe(viewLifecycleOwner) {
            if (it != null) {
                locationList = it
                val distance = calculateDistance(locationList)
                if (locationList.size > 1) {
                    trackingViewModel.downLoadImage(locationList.last(), distance / 100)
                }
                binding.distanceValue.text =
                    getString(R.string.initial_distance, formatDistance(distance))

            }
        }

        trackingViewModel.imageList.observe(viewLifecycleOwner) {

        }
    }

    private fun updateMenuAndStartTracking() {
        if (binding.toolbar.startBtn.text == getString(R.string.start)) {
            binding.toolbar.startBtn.text = getString(R.string.stop)
            onStartButtonClicked()
        } else {
            binding.toolbar.startBtn.text = getString(R.string.start)
            onStopButtonClicked()
        }
    }

    private fun onStartButtonClicked() {
        if (hasBackgroundLocationPermission(requireContext())) {
            sendActionCommandToService(ACTION_START_FOREGROUND_SERVICE)
        } else {
            requestBackgroundLocationPermission(this)
        }
    }

    private fun onStopButtonClicked() {
        stopForegroundService()
    }

    private fun stopForegroundService() {
        sendActionCommandToService(ACTION_STOP_FOREGROUND_SERVICE)
    }

    private fun sendActionCommandToService(action: String) {
        Intent(requireContext(), TrackingService::class.java).apply {
            this.action = action
            requireContext().startService(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, listOf(perms[0]))) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestBackgroundLocationPermission(this)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        onStartButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}