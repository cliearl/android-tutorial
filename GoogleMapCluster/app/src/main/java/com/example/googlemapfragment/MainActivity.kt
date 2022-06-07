package com.example.googlemapfragment

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.googlemapfragment.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigation()

        if (!isAllPermissionsGranted()) {
            requestMultiplePermissions.launch(REQUIRED_PERMISSIONS)
        }
    }

    private fun setBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_info -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, InfoFragment()).commit()
                    true
                }
                R.id.navigation_map -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun isAllPermissionsGranted(): Boolean = REQUIRED_PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(
            this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { permission ->
                when {
                    permission.value -> {
                        Snackbar.make(binding.root,
                            "Permission granted", Snackbar.LENGTH_SHORT).show()
                    }
                    shouldShowRequestPermissionRationale(permission.key) -> {
                        Snackbar.make(binding.root,
                            "Permission required to use app!", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Snackbar.make(binding.root,
                        "Permission denied", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,  // 도시 블록 단위
            Manifest.permission.ACCESS_FINE_LOCATION,  // 더 정밀한 단위
        )
    }
}