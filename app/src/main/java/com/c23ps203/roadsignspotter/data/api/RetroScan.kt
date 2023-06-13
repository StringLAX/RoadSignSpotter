package com.c23ps203.roadsignspotter.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroScan {

    fun getRetroClientInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    companion object {
        private const val BASE_URL = "https://machinelearning-gb7riya7aq-uc.a.run.app" // API DEPLOY ML AKUN BANGKIT (Cloud Run)
    }
}