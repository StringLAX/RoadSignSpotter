package com.c23ps203.roadsignspotter.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("username")
    @Expose
    var username: String? = null,

    @SerializedName("password")
    @Expose
    var password: String? = null
)
