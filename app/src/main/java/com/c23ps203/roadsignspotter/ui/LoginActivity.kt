package com.c23ps203.roadsignspotter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Login"

        binding.btnLogin.setOnClickListener {

                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginPassword.text.toString()

                when {
                    email.isEmpty() || password.isEmpty() -> {
                        binding.edLoginEmail.error = resources.getString(R.string.email_error)
                        binding.edLoginPassword.error = resources.getString(R.string.password_error)
                    }
                    password.length < 8 -> {
                        binding.edLoginPassword.error = resources.getString(R.string.password_error)
                    }
                    else -> {
                        // testing intent
                        intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
        }

        binding.btnToRegister.setOnClickListener {
            intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}