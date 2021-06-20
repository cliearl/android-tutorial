package com.example.lottogenerator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lottogenerator.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.generatebutton.setOnClickListener {
            val lottoNumbers = createLottoNumbers()
            Log.d("TAG", lottoNumbers.toString())
            updateLottoBallImage(lottoNumbers)
        }
    }

    private fun createLottoNumbers(): ArrayList<Int> {
        val result = arrayListOf<Int>()

        val source = IntArray(45) { it + 1 }
        val seed =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.KOREA).format(Date()).hashCode()
                .toLong()
        val random = Random(seed)
        source.shuffle(random)
        source.slice(0..5).forEach { num ->
            result.add(num)
        }
        result.sort()

        var evenNumberCount = 0
        var oddNumberCount = 0
        for (num in result) {
            if (num % 2 == 0) {
                evenNumberCount += 1
            } else {
                oddNumberCount += 1
            }
        }
        result.add(result.sum())
        result.add(oddNumberCount)
        result.add(evenNumberCount)

        return result
    }

    private fun getDrawableID(number: Int): Int {
        val number = String.format("%02d", number)
        val string = "ball_$number"
        val id = resources.getIdentifier(string, "drawable", packageName)
        return id
    }

    private fun updateLottoBallImage(result: ArrayList<Int>) {
        with(binding) {
            ivGame0.setImageResource(getDrawableID(result[0]))
            ivGame1.setImageResource(getDrawableID(result[1]))
            ivGame2.setImageResource(getDrawableID(result[2]))
            ivGame3.setImageResource(getDrawableID(result[3]))
            ivGame4.setImageResource(getDrawableID(result[4]))
            ivGame5.setImageResource(getDrawableID(result[5]))
            tvAnalyze.text = "번호합: ${result[6]}  홀:짝=${result[7]}:${result[8]}"
        }
    }
}