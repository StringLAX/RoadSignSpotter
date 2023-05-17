package com.c23ps203.roadsignspotter.data.viewmodel

import androidx.lifecycle.ViewModel
import com.c23ps203.roadsignspotter.data.api.Api
import com.c23ps203.roadsignspotter.data.api.Retro
import com.c23ps203.roadsignspotter.data.model.request.RegisterRequest
import com.c23ps203.roadsignspotter.data.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        onSuccess: (RegisterResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val request = RegisterRequest()
        request.name = name
        request.username = username
        request.email = email
        request.password = password

        val retro = Retro().getRetroClientInstance().create(Api::class.java)
        retro.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val user = response.body()
                if (user != null) {
                    onSuccess(user)
                } else {
                    onFailure("Registration failed")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onFailure(t.message ?: "Registration failed")
            }
        })
    }
}