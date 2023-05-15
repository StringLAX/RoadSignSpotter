package com.c23ps203.roadsignspotter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.c23ps203.roadsignspotter.data.api.Api
import com.c23ps203.roadsignspotter.data.api.Retro
import com.c23ps203.roadsignspotter.data.helper.Constant
import com.c23ps203.roadsignspotter.data.helper.PreferenceHelper
import com.c23ps203.roadsignspotter.data.model.request.ChangePasswordRequest
import com.c23ps203.roadsignspotter.data.model.response.ChangePasswordResponse
import com.c23ps203.roadsignspotter.databinding.ActivityChangePasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Change Password"

        sharedPref = PreferenceHelper(this)

        binding.btnChangePassword.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword() {
        val request = ChangePasswordRequest()
        val token = "Bearer ${sharedPref.getString(Constant.prefToken)}"
        request.userId = sharedPref.getString(Constant.prefUserId)
        request.currentPassword = binding.edOldPassword.text.toString()
        request.newPassword = binding.edNewPassword.text.toString()

        val retro = Retro().getRetroClientInstance().create(Api::class.java)
        retro.changePassword(token, request).enqueue(object : Callback<ChangePasswordResponse>{
            override fun onResponse(
                call: Call<ChangePasswordResponse>,
                response: Response<ChangePasswordResponse>
            ) {
                val user = response.body()
                if (response.isSuccessful) {
                    Log.d("ChangePasswordActivity", "onResponse: ${user?.message}")
                    Toast.makeText(this@ChangePasswordActivity, user?.message, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ChangePasswordActivity, ProfileActivity::class.java))
                } else {
                    Log.d("ChangePasswordActivity", "Password change failed")
                    Toast.makeText(this@ChangePasswordActivity, user?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }
}