package com.example.googlemapfragment.cluster

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class CoronaMedClusterItem(
    private val _position: LatLng,
    private val _title: String,
    private val _snippet: String,
    private val _icon: BitmapDescriptor,
) :
    ClusterItem {
    override fun getSnippet(): String {
        return _snippet
    }

    override fun getTitle(): String {
        return _title
    }

    override fun getPosition(): LatLng {
        return _position
    }

    fun getIcon(): BitmapDescriptor {
        return _icon
    }
}