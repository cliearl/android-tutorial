package com.example.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sensorlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getSensorList()
    }

    private fun getSensorList() {
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        var sensorResult = String()
        sensorList.forEachIndexed { index, sensor ->
            sensorResult += """
                $index : ${sensor.name}
                toString : $sensor
                
                
            """.trimIndent()
        }
        binding.tvSensor.text = sensorResult
    }
}