package com.satya.passwordvault.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class LoginWorker(
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("LoginWorker", "Background task running")

        val username = inputData.getString("username")
        val password = inputData.getString("password")

        // Perform login logic here
        Log.d("TAG_Debug", "doWork: $username & $password")

        return Result.success(
            workDataOf("result" to "Worker Executed Successfully!")
        )
    }
}