package com.example.gadmobbanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gadmobbanner.databinding.ActivityMainBinding
import com.google.android.gms.ads.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showBannerAd()
    }

    private fun showBannerAd() {
        MobileAds.initialize(this) {}

        val testDeviceIds = listOf("My device ID1", "My Device ID2")
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build()
        )

        mAdView = binding.adView
        val bannerAdRequest = AdRequest.Builder().build()
        mAdView.loadAd(bannerAdRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Toast.makeText(applicationContext, "Banner Ad loaded", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                Toast.makeText(applicationContext, "Banner Ad loading failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        mAdView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mAdView.resume()
    }

    override fun onDestroy() {
        mAdView.destroy()
        super.onDestroy()
    }
}