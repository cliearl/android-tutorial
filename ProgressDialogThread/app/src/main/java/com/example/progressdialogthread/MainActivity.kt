package com.example.progressdialogthread

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AlertDialog
import com.example.progressdialogthread.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            progressDialog = ProgressDialog(this)
            with(progressDialog) {
                max = 100
                setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                setTitle("Download Task")
                setMessage("Please wait, we are downloading your files...")
                setCancelable(false)
                show()
            }

            val handler: Handler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    progressDialog.incrementProgressBy(10)
                }
            }

            Thread {
                try {
                    while (progressDialog.progress <= progressDialog.max) {
                        Thread.sleep(200)
                        handler.sendMessage(handler.obtainMessage())
                        if (progressDialog.progress >= progressDialog.max) {
                            progressDialog.dismiss()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
    }
}