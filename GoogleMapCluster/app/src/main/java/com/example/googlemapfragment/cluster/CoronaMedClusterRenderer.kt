package com.example.googlemapfragment.cluster

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CoronaMedClusterRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<CoronaMedClusterItem>,
) : DefaultClusterRenderer<CoronaMedClusterItem>(context, map, clusterManager) {
    init {
        clusterManager.renderer = this
    }

    override fun onBeforeClusterItemRendered(item: CoronaMedClusterItem, markerOptions: MarkerOptions) {
        markerOptions.icon(item.getIcon())
        markerOptions.visible(true)
    }
}