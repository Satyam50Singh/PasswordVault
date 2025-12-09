package com.satya.passwordvault

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.satya.passwordvault.databinding.ActivityMainBinding
import com.satya.passwordvault.presentation.PasswordVaultApp
import io.flutter.embedding.android.FlutterActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
            PasswordVaultApp(doLogin = { username, password ->
                // Handle login logic here
                doSubmit(username, password)
            })
        }
    }

    private fun doSubmit(username: String, password: String) {
        if (username.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_a_username), Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_a_password), Toast.LENGTH_SHORT).show()
            return
        }

        // Send username and password to Flutter
        BaseApplication.methodChannel?.invokeMethod("sendUsername",  username)

        val intent = FlutterActivity
            .withCachedEngine("login_flutter")
            .build(this)

        startActivity(intent)
    }

}

