package com.c23ps203.roadsignspotter.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse (

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("username")
    @Expose
    var username: String,

    @SerializedName("token")
    @Expose
    var token: String,

    @SerializedName("userId")
    @Expose
    var userId: String,

    @SerializedName("email")
    @Expose
    var email: String
)