package com.satya.passwordvault

import android.app.Application
import android.widget.Toast
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class BaseApplication: Application() {

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
                "closeFlutter" -> {
                    Toast.makeText(applicationContext, "Flutter closed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        FlutterEngineCache.getInstance()
            .put("login_flutter", flutterEngine)

    }
}