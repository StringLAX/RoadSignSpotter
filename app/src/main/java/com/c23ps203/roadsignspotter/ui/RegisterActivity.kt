package com.c23ps203.roadsignspotter.ui

import com.c23ps203.roadsignspotter.data.viewmodel.RegisterViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Register"
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

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
                    showLoading(true)
                    register(name, username, email, password)
                }
            }
        }
    }

    private fun register(name: String, username: String, email: String, password: String) {
        viewModel.register(name, username, email, password, {
            Log.d("Register Result", it.message.toString())
            Log.d("Register Result", it.userId.toString())
            Toast.makeText(this@RegisterActivity, it.message, Toast.LENGTH_SHORT).show()
            showLoading(false)
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }, { errorMessage ->
            showLoading(false)
            Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}