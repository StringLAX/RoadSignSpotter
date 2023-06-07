package com.c23ps203.roadsignspotter.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ScanResponse (

    @SerializedName("label")
    @Expose
    var label: String,

    @SerializedName("confidence")
    @Expose
    var confidence: String
)
