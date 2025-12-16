package com.satya.passwordvault.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.satya.passwordvault.utils.Tags

class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e(Tags.TAG, "onReceive: Download Starts")

        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            // get downloadId from data store
            Log.e(Tags.TAG, "onReceive: Download Completed")
        }
    }
}