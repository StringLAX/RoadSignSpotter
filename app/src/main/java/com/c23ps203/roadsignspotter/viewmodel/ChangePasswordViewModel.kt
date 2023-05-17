package com.c23ps203.roadsignspotter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.c23ps203.roadsignspotter.data.api.Api
import com.c23ps203.roadsignspotter.data.api.Retro
import com.c23ps203.roadsignspotter.data.helper.Constant
import com.c23ps203.roadsignspotter.data.helper.PreferenceHelper
import com.c23ps203.roadsignspotter.data.model.request.ChangePasswordRequest
import com.c23ps203.roadsignspotter.data.model.response.ChangePasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel : ViewModel() {

    private lateinit var sharedPref: PreferenceHelper

    fun init(sharedPref: PreferenceHelper) {
        this.sharedPref = sharedPref
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val request = ChangePasswordRequest()
        val token = "Bearer ${sharedPref.getString(Constant.prefToken)}"
        request.userId = sharedPref.getString(Constant.prefUserId)
        request.currentPassword = currentPassword
        request.newPassword = newPassword

        val retro = Retro().getRetroClientInstance().create(Api::class.java)
        retro.changePassword(token, request).enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(
                call: Call<ChangePasswordResponse>,
                response: Response<ChangePasswordResponse>
            ) {
                val user = response.body()
                if (response.isSuccessful) {
                    onSuccess.invoke()
                    Log.d("ChangePassword", "Password changed successfully")
                } else {
                    onFailure.invoke(user?.message ?: "Password change failed")
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                onFailure.invoke(t.message.toString())
            }
        })
    }
}