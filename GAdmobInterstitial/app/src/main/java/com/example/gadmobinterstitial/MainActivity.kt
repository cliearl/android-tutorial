package com.example.gadmobinterstitial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gadmobinterstitial.databinding.ActivityMainBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)

        val testDeviceIds = listOf("My device ID1", "My device ID2")
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build()
        )

        loadInterstitialAd()

        binding.button.setOnClickListener {
            showInterstitialad()
        }
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd?.adUnitId = "ca-app-pub-3940256099942544/1033173712"
//        mInterstitialAd?.loadAd(adRequest)
//        mInterstitialAd?.adListener = object : AdListener() {
//            override fun onAdLoaded() {
//                Toast.makeText(applicationContext, "Ad loading succeed", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onAdFailedToLoad(p0: LoadAdError?) {
//                Toast.makeText(applicationContext, "Ad loading failed", Toast.LENGTH_SHORT).show()
//                mInterstitialAd = null
//            }
//
//            override fun onAdClosed() {
//                mInterstitialAd?.loadAd(adRequest)
//            }
//        }

        InterstitialAd.load(
            this, "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Toast.makeText(applicationContext, "Ad loading failed", Toast.LENGTH_SHORT).show()
                    mInterstitialAd = null
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    Toast.makeText(applicationContext, "Ad loading succeed", Toast.LENGTH_SHORT).show()
                    mInterstitialAd = p0
                }
            }
        )
    }

    private fun showInterstitialad() {
        if (mInterstitialAd != null) {
//            if (mInterstitialAd!!.isLoaded) {
//                mInterstitialAd?.show()
//            }

            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d("TAG", "Ad was dismissed.")
                    mInterstitialAd = null
                    loadInterstitialAd()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError?) {
                    Log.d("TAG", "Ad failed to show.")
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("TAG", "Ad showed fullscreen content")
                }
            }
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad was not ready yet")
        }
    }
}