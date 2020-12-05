package com.example.autobuildnum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val versionNumber = BuildConfig.VERSION_NAME.replace("'", "")
    private val versionBuild = BuildConfig.VERSION_CODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.text = "App Version: $versionNumber ($versionBuild)"
    }
}