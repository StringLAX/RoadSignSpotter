package com.c23ps203.roadsignspotter.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest (

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("password")
    @Expose
    var password: String? = null
)