package com.example.gadmobrewarded

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gadmobrewarded.databinding.ActivityMainBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mRewardedAd: RewardedAd? = null
    private var reward: Int = 0

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

        loadRewardedAd()

        binding.button.setOnClickListener {
            showRewardedAd()
        }
    }

    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()

//        mRewardedAd = RewardedAd(this, "ca-app-pub-3940256099942544/5224354917")
//        mRewardedAd?.loadAd(adRequest, object : RewardedAdLoadCallback() {
//            override fun onRewardedAdFailedToLoad(p0: LoadAdError?) {
//                Log.d("TAG", p0!!.message)
//                mRewardedAd = null
//            }
//
//            override fun onRewardedAdLoaded() {
//                Toast.makeText(applicationContext, "Ad loading succeed", Toast.LENGTH_SHORT).show()
//            }
//        })

        RewardedAd.load(
            this, "ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Log.d("TAG", p0.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(p0: RewardedAd) {
                    Toast.makeText(applicationContext, "Ad loading succeed", Toast.LENGTH_SHORT).show()
                    mRewardedAd = p0
                }
            }
        )
    }

    private fun showRewardedAd() {
        if (mRewardedAd != null) {
//            if (mRewardedAd!!.isLoaded) {
//                mRewardedAd?.show(this, object : RewardedAdCallback() {
//                    override fun onUserEarnedReward(p0: RewardItem) {
//                        Toast.makeText(applicationContext, "onUserEarnedReward", Toast.LENGTH_SHORT).show()
//                        val rewardAmount = p0.amount
//                        val rewardType = p0.type
//                        reward += rewardAmount
//                        binding.textview.text = "$rewardType : $reward"
//                    }
//
//                    override fun onRewardedAdClosed() {
//                        loadRewardedAd()
//                    }
//
//                    override fun onRewardedAdFailedToShow(p0: AdError?) {
//                        Log.d("TAG", p0!!.message)
//                        mRewardedAd = null
//                    }
//
//                    override fun onRewardedAdOpened() {
//                        Log.d("TAG", "onRewardedAdOpened")
//                    }
//                })
//            }

            mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d("TAG", "Ad was dismissed")
                    mRewardedAd = null
                    loadRewardedAd()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError?) {
                    Log.d("TAG", "Ad failed to show.")
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("TAG", "Ad showed fullscreen content.")
                    mRewardedAd = null
                }
            }
            mRewardedAd?.show(this, OnUserEarnedRewardListener() { rewardItem ->
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                reward += rewardAmount
                binding.textview.text = "$rewardType : $reward"
            })
        } else {
            Log.d("TAG", "The rewarded ad was not loaded yet")
        }
    }
}