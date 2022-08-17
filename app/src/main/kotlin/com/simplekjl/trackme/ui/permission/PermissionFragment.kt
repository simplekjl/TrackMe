package com.simplekjl.trackme.ui.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.simplekjl.trackme.R
import com.simplekjl.trackme.databinding.FragmentPermissionBinding
import com.simplekjl.trackme.ui.permission.Permissions.hasLocationPermission
import com.simplekjl.trackme.ui.permission.Permissions.requestLocationPermission
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog


class PermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)

        binding.btnContinue.setOnClickListener {
            if (hasLocationPermission(requireContext())) {
                findNavController(binding.root).navigate(R.id.resultsFragment)
            } else
                requestLocationPermission(this)
        }

        return binding.root
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
        if (EasyPermissions.somePermissionPermanentlyDenied(this, listOf(perms[0])))
            SettingsDialog.Builder(requireActivity()).build().show()
        else
            requestLocationPermission(this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        findNavController(binding.root).navigate(R.id.resultsFragment)
    }

}