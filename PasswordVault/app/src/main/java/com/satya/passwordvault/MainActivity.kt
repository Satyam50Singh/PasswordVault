package com.satya.passwordvault

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.satya.passwordvault.databinding.ActivityMainBinding
import com.satya.passwordvault.presentation.PasswordVaultApp
import com.satya.passwordvault.worker.LoginWorker
import io.flutter.embedding.android.FlutterActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the insets for the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initCompose()
    }

    fun initCompose() {
        binding.composeView.setContent {
            PasswordVaultApp(
                doLogin = { username, password ->
                    // Handle login logic here
                    doSubmit(username, password)
                },
                downloadPdf = {
                    val url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf\n"
                    val request = DownloadManager.Request(Uri.parse(url))
                        .setTitle("PasswordVault")
                        .setDescription("Collection of all the passwords")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)
                        .setMimeType("application/pdf")
                        .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            "PasswordVault.pdf"
                        )

                    val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                    downloadManager.enqueue(request)
                    Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show()
                }
            )
        }
        workManager = WorkManager.getInstance(this)


    }

    private fun doSubmit(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val workRequest = OneTimeWorkRequestBuilder<LoginWorker>()
            .setInputData(
                workDataOf(
                    "username" to username,
                    "password" to password
                )
            )
            .build()

        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this) { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    val result: String? = workInfo.outputData.getString("result")
                    result?.let {
                        Toast.makeText(this, "Login Successful: $it", Toast.LENGTH_SHORT).show()

                        // Send username and password to Flutter
                        BaseApplication.methodChannel?.invokeMethod("sendUsername", username)

                        val intent = FlutterActivity
                            .withCachedEngine("login_flutter")
                            .build(this)

                        startActivity(intent)
                    }
                }
            }

        workManager.enqueue(workRequest)

        // Periodic WorkManager
        // PeriodicWorkRequest has a minimum interval of 15 minutes enforced by Android OS.

        // val periodicWorkRequest = PeriodicWorkRequestBuilder<LoginWorker>(15, TimeUnit.MINUTES)
        //    .build()

        // workManager.enqueue(periodicWorkRequest)
    }
}

