package com.androidsicredi.oauth2.model

import com.google.gson.annotations.SerializedName

class Token {
    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("refresh_token")
    var refreshToken: String? = null

}