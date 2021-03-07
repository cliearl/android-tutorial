package com.example.sharedpreferencebasic

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharedpreferencebasic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            savePref()

        }
        binding.buttonLoad.setOnClickListener {
            loadPref()

        }
    }

    private fun savePref() {
        val sharedPreferences = getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt(KEY_GRAPHIC, binding.radioGraphics.checkedRadioButtonId)
        editor.putInt(KEY_MUSIC, binding.seekBarMusic.progress)
        editor.putInt(KEY_SFX, binding.seekBarSfx.progress)
        editor.putBoolean(KEY_VSYNC, binding.switchVsync.isChecked)

        editor.apply()
        Toast.makeText(applicationContext, "Game settings has saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadPref() {
        val sharedPreferences = getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(KEY_GRAPHIC)) {
            val graphicValue = sharedPreferences.getInt(KEY_GRAPHIC, 0)
            val musicValue = sharedPreferences.getInt(KEY_MUSIC, 50)
            val sfxValue = sharedPreferences.getInt(KEY_SFX, 50)
            val vsyncValue = sharedPreferences.getBoolean(KEY_VSYNC, true)

            binding.radioGraphics.check(graphicValue)
            binding.seekBarMusic.progress = musicValue
            binding.seekBarSfx.progress = sfxValue
            binding.switchVsync.isChecked = vsyncValue

            Toast.makeText(applicationContext, "Game settings has loaded", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private const val KEY_PREFS = "game_settings"
        private const val KEY_GRAPHIC = "graphic_quality"
        private const val KEY_MUSIC = "music_volume"
        private const val KEY_SFX = "sfx_volume"
        private const val KEY_VSYNC = "vertical_sync"
    }
}