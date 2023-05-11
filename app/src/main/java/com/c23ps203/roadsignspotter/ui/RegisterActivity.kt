package com.c23ps203.roadsignspotter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.data.api.Api
import com.c23ps203.roadsignspotter.data.api.Retro
import com.c23ps203.roadsignspotter.data.model.request.RegisterRequest
import com.c23ps203.roadsignspotter.data.model.response.RegisterResponse
import com.c23ps203.roadsignspotter.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    register()
                }
            }
        }
    }

    private fun register() {
        val request = RegisterRequest()
        request.name = binding.edRegisterName.text.toString()
        request.username = binding.edRegisterUsername.text.toString()
        request.email = binding.edRegisterEmail.text.toString()
        request.password = binding.edRegisterPassword.text.toString()

        val retro = Retro().getRetroClientInstance().create(Api::class.java)
        retro.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>, response: Response<RegisterResponse>
            ) {
                val user = response.body()
                if (user != null) {
                    Log.e("Register Result", user.toString())
                    intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@RegisterActivity, R.string.email_exist, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}