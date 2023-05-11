package com.c23ps203.roadsignspotter.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null
}