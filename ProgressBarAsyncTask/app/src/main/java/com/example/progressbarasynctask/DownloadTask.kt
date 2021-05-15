package com.example.progressbarasynctask

import android.os.AsyncTask
import android.widget.Toast
import java.lang.ref.WeakReference

class DownloadTask(activity: MainActivity) : AsyncTask<String, Int, String>() {
    private val weakReference: WeakReference<MainActivity> = WeakReference(activity)

    override fun doInBackground(vararg p0: String?): String {
        val count = p0.size

        for (i in 0 until count) {
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            publishProgress(((i + 1) / count.toFloat() * 100).toInt())
            if (isCancelled) break
        }

        return "Download Completed"
    }

    override fun onProgressUpdate(vararg values: Int?) {
        val activity = weakReference.get()
        if (activity == null || activity.isFinishing) return

        activity.binding.textview.text = "Progress : ${values[0]}"
        activity.binding.progressbar.progress = values[0]!!
    }

    override fun onPostExecute(result: String?) {
        val activity = weakReference.get()
        if (activity == null || activity.isFinishing) return

        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
    }

    override fun onCancelled() {
        val activity = weakReference.get()
        if (activity == null || activity.isFinishing) return

        activity.binding.textview.text = "Progress : 0"
        activity.binding.progressbar.progress = 0
    }
}