package com.example.dateandtime

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Date 표현
        val utilDate = Date()
//        textView.text = utilDate.toString()

        val formatType = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        textView.text = formatType.format(utilDate)

        // java.time 라이브러리 사용하기
        val timeDate: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.now()
            val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val nowString = date.format(dtf)
            timeDate = nowString
        } else {
            val date = org.joda.time.LocalDateTime.now()
            val dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val jodatime = dtf.parseDateTime(date.toString())
            val nowString = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(jodatime)
            timeDate = nowString
        }

        textView2.text = timeDate

        // 날짜 차이 구하기
        val period: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val startDate = LocalDate.of(2000, 1, 1)
            val todayDate = LocalDate.now()
            val differenceDate = startDate.until(todayDate, ChronoUnit.DAYS) + 1
            period = differenceDate.toInt()
        } else {
            val startDate = org.joda.time.LocalDate(2000, 1, 1)
            val todayDate = org.joda.time.LocalDate.now()
            val differenceDate = Days.daysBetween(startDate, todayDate).days + 1
            period = differenceDate
        }

        textView2.text = period.toString()
    }
}