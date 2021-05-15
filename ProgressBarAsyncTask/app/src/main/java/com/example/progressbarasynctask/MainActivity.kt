package com.example.progressbarasynctask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.progressbarasynctask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    internal lateinit var binding: ActivityMainBinding
    private var downloadTask: DownloadTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.downloadbutton.setOnClickListener {
            downloadTask = DownloadTask(this).apply {
                execute("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
            }
        }

        binding.cancelbutton.setOnClickListener {
            downloadTask?.cancel(true)

        }
    }

    override fun onPause() {
        downloadTask?.cancel(true)
        super.onPause()
    }
}