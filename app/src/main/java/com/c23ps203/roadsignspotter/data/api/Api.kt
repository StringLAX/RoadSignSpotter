package com.c23ps203.roadsignspotter.data.api

import com.c23ps203.roadsignspotter.data.model.request.LoginRequest
import com.c23ps203.roadsignspotter.data.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}