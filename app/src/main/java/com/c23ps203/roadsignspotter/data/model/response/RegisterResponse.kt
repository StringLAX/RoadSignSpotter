package com.c23ps203.roadsignspotter.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegisterResponse {

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("userId")
    @Expose
    var userId: Int? = null
}