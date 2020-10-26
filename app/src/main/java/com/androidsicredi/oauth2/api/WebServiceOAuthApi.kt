package com.androidsicredi.oauth2.api

import com.androidsicredi.oauth2.model.*
import retrofit2.Call
import retrofit2.http.*

interface WebServiceOAuthApi {


    @GET("http://5f5a8f24d44d640016169133.mockapi.io/api/events")
    fun getListEvent(): Call<List<Event?>>


}