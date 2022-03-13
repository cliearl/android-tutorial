package com.example.osslicenses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.osslicenses.databinding.ActivityMainBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스 목록")
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }
    }
}