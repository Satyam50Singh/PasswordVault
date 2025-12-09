package com.satya.passwordvault

import android.app.Application
import android.content.Intent
import android.widget.Toast
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class BaseApplication : Application() {

    companion object {
        const val LOGIN_CHANNEL = "com.satya.passwordvault/login_channel"
        var methodChannel: MethodChannel? = null
    }

    override fun onCreate() {
        super.onCreate()

        val flutterEngine = FlutterEngine(this)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        flutterEngine.navigationChannel.setInitialRoute("/");

        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, LOGIN_CHANNEL)
        methodChannel?.setMethodCallHandler { call, result ->
            when (call.method) {
                "saveRecord" -> {
                    val record = call.arguments as Map<String, Any>

                    val category = record["category"] as String
                    val platform = record["platform"] as String
                    val username = record["username"] as String
                    val password = record["password"] as String

                    Toast.makeText(this, "New record: $category, $platform, $username, $password", Toast.LENGTH_SHORT).show()

                    // Optionally navigate to another native Activity
                    val intent = Intent(this, DataViewActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("username", username)
                    startActivity(intent)
                }

                "closeFlutter" -> {
                    Toast.makeText(applicationContext, "Flutter closed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        FlutterEngineCache.getInstance()
            .put("login_flutter", flutterEngine)

    }
}