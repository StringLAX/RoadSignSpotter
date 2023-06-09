package com.c23ps203.roadsignspotter.data.api

import com.c23ps203.roadsignspotter.data.model.request.ChangePasswordRequest
import com.c23ps203.roadsignspotter.data.model.request.LoginRequest
import com.c23ps203.roadsignspotter.data.model.request.RegisterRequest
import com.c23ps203.roadsignspotter.data.model.response.ChangePasswordResponse
import com.c23ps203.roadsignspotter.data.model.response.LoginResponse
import com.c23ps203.roadsignspotter.data.model.response.RegisterResponse
import com.c23ps203.roadsignspotter.data.model.response.ScanResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("/auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("/auth/register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("/auth/change-password")
    fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Call<ChangePasswordResponse>

    @Multipart
    @POST("/predict")
    fun scanImage(
        @Part file: MultipartBody.Part
    ): Call<ScanResponse>
}