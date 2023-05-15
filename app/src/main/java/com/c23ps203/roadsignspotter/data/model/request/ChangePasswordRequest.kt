package com.c23ps203.roadsignspotter.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest (

    @SerializedName("userId")
    @Expose
    var userId: String? = null,

    @SerializedName("currentPassword")
    @Expose
    var currentPassword: String? = null,

    @SerializedName("newPassword")
    @Expose
    var newPassword: String? = null,
)