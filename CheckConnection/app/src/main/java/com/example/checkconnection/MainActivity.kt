package com.example.checkconnection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG", getNetworkType(this))
        Log.d("TAG", isOnline().toString())
    }

    private fun getNetworkType(context: Context): String {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connMgr.activeNetwork ?: return "No_Connection"
            val actNw = connMgr.getNetworkCapabilities(nw) ?: return "No_Connection"
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wifi_State"
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular_State"
                else -> "No_Connection"
            }
        } else {
            val nwInfo = connMgr.activeNetworkInfo ?: return "No_Connection"
            return nwInfo.typeName
        }
    }

    private class CheckConnection(private val host: String) : Thread() {
        var isSuccess = false

        override fun run() {
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = URL(host).openConnection() as HttpURLConnection
                urlConnection.setRequestProperty("User-Agent", System.getProperty("http.agent"))
                urlConnection.connectTimeout = 1000
                urlConnection.connect()
                val responseCode = urlConnection.responseCode
                if (responseCode == 204) {
                    isSuccess = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            urlConnection?.disconnect()
        }
    }

    private fun isOnline(): Boolean {
        val cc = CheckConnection("http://clients3.google.com/generate_204")
        cc.start()
        try {
            cc.join()
            return cc.isSuccess
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}