package com.example.googlemapfragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.googlemapfragment.cluster.CoronaMed
import com.example.googlemapfragment.cluster.CoronaMedClusterItem
import com.example.googlemapfragment.cluster.CoronaMedClusterRenderer
import com.example.googlemapfragment.cluster.CoronaMedItem
import com.example.googlemapfragment.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.maps.android.clustering.ClusterManager

class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    // 현재위치 확인하기
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // 아이콘 추가
    private val mapIcon by lazy {
        val drawable =
            ResourcesCompat.getDrawable(resources, R.drawable.mapicon, null) as BitmapDrawable
        Bitmap.createScaledBitmap(drawable.bitmap, 64, 64, false)
    }
    private lateinit var clusterManager: ClusterManager<CoronaMedClusterItem>



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync(this)
        binding.mapView.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

//        try {
//            map.isMyLocationEnabled = true
//            map.uiSettings.isMyLocationButtonEnabled = true
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

        binding.btnFab.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                if (location != null) {
                    val mylocation = LatLng(location.latitude, location.longitude)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(mylocation, 14f)
                    map.animateCamera(cameraUpdate)
                }
            }
        }

        val initialPosition = LatLng(37.5638155, 126.9856765)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(initialPosition, 14f)
        map.animateCamera(cameraUpdate)

//        map.addMarker(
//            MarkerOptions()
//                .position(initialPosition)
//                .title("서울시청")
//        )

        clusterManager = ClusterManager(requireContext(), map)
        CoronaMedClusterRenderer(requireContext(), map, clusterManager)
        map.setOnCameraIdleListener(clusterManager)

        addMarkers()
    }

    private fun getJsonData(filename: String): CoronaMed {
        var result = CoronaMed()

        try {
            val assetManager = resources.assets
            val inputStream = assetManager.open(filename)
            val reader = inputStream.bufferedReader()
            val gson = Gson()
            result = gson.fromJson(reader, CoronaMed::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private fun addMarkers() {
        val items = getJsonData("data.json")
        for (item in items) {
            val medClusterItem = CoronaMedClusterItem(
                LatLng(item.REFINE_WGS84_LAT.toDouble(), item.REFINE_WGS84_LOGT.toDouble()),
                item.MEDINST_NM,
                "타입: ${item.DISTRCT_DIV_DTLS}",
                BitmapDescriptorFactory.fromBitmap(mapIcon)
            )
            clusterManager.addItem(medClusterItem)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

//    override fun onDestroy() {
//        binding.mapView.onDestroy()
//        super.onDestroy()
//    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}