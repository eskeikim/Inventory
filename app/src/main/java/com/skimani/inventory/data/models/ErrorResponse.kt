package com.skimani.inventory.data.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @SerializedName("error_code")
    val errorCode: String,

    @SerializedName("error_message")
    val errorMessage: String

)
