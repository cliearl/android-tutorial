package com.example.tiltsensor

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tiltsensor.databinding.ActivityMainBinding
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val r = sqrt(x.pow(2) + y.pow(2) + z.pow(2))

            Log.d("TAG", "onSensorChanged: x: $x, y: $y, z: $z, R: $r")
            val xrAngle = (90 - acos(x / r) * 180 / PI).toFloat()
            val yrAngle = (90 - acos(y / r) * 180 / PI).toFloat()

            binding.textview.text = String.format(
                "x-rotation: %.1f\u00B0 \n y-rotation: %.1f\u00B0", xrAngle, yrAngle
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) { }
}