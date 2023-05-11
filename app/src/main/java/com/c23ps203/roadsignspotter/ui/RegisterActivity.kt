package com.c23ps203.roadsignspotter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Register"
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val username = binding.edRegisterUsername.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            when {
                name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() -> {
                    binding.edRegisterName.error = resources.getString(R.string.name_empty)
                    binding.edRegisterUsername.error = resources.getString(R.string.username_empty)
                    binding.edRegisterEmail.error = resources.getString(R.string.email_empty)
                    binding.edRegisterPassword.error = resources.getString(R.string.password_empty)
                }
                password.length < 8 -> {
                    resources.getString(R.string.password_error)
                }
                else -> {
                    // testing intent
                    intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}