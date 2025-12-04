package com.satya.passwordvault

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.satya.passwordvault.databinding.ActivityMainBinding
import com.satya.passwordvault.presentation.PasswordVaultApp

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
        Toast.makeText(this, "Welcome User!", Toast.LENGTH_SHORT).show()
    }

}

