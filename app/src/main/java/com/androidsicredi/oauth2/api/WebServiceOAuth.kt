package com.androidsicredi.oauth2.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebServiceOAuth private constructor() {
    private val retrofit: Retrofit
    private var httpClientBuilder: OkHttpClient.Builder
    fun <S> createService(serviceClass: Class<S>?): S {
        return retrofit.create(serviceClass)
    }

    /*
    fun <S> createServiceWithOAuth2(serviceClass: Class<S>?, tokenManager: TokenManager): S {
        val newClient = httpClientBuilder.addInterceptor { chain ->
            val requestOriginal = chain.request()
            val builder = requestOriginal.newBuilder()
            if (tokenManager.token.accessToken != null) {
                builder.addHeader("Authorization", "Bearer" + tokenManager.token.accessToken)
            }
            val request = builder.build()
            chain.proceed(request)
        }.authenticator(CustomAuthenticator.getInstance(tokenManager)).build()
        return retrofit.newBuilder().client(newClient).build().create(serviceClass)
    }

     */

    companion object {
        private const val BASE_URL = "http://5f5a8f24d44d640016169133.mockapi.io/api/"
        private lateinit var loggingInterceptor: HttpLoggingInterceptor

        @JvmStatic
        @get:Synchronized
        var instance: WebServiceOAuth? = null
            get() {
                if (field == null) {
                    field = WebServiceOAuth()
                }
                return field
            }
            private set
    }

    init {
        httpClientBuilder = OkHttpClient.Builder()
        loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClientBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}