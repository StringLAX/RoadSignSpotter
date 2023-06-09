package com.c23ps203.roadsignspotter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.data.api.Api
import com.c23ps203.roadsignspotter.data.api.Retro
import com.c23ps203.roadsignspotter.data.helper.Constant
import com.c23ps203.roadsignspotter.data.helper.PreferenceHelper
import com.c23ps203.roadsignspotter.data.model.request.LoginRequest
import com.c23ps203.roadsignspotter.data.model.response.LoginResponse
import com.c23ps203.roadsignspotter.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = PreferenceHelper(this)

        supportActionBar?.hide()

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
                    showLoading(true)
                    login()
                }
            }
        }

        binding.btnToRegister.setOnClickListener {
            intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val request = LoginRequest()
        request.identifier = binding.edLoginEmail.text.toString()
        request.password = binding.edLoginPassword.text.toString()

        val retro = Retro().getRetroClientInstance().create(Api::class.java)
        retro.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    sharedPref.put(Constant.prefIsLogin, true)
                    sharedPref.put(Constant.prefToken, "${response.body()?.token}")
                    sharedPref.put(Constant.prefName, "${response.body()?.name}")
                    sharedPref.put(Constant.prefUsername, "${response.body()?.username}")
                    sharedPref.put(Constant.prefEmail, "${response.body()?.email}")
                    sharedPref.put(Constant.prefUserId, "${response.body()?.userId}")
                    Log.d("message", "${response.body()?.message}")
                    Toast.makeText(this@LoginActivity, R.string.login_success, Toast.LENGTH_SHORT)
                        .show()
                    showLoading(false)
                    moveIntent()
                }
                else {
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, R.string.unauthorized, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@LoginActivity, R.string.login_failed, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(Constant.prefIsLogin)) {
            moveIntent()
        }
    }

    private fun moveIntent() {
        intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}