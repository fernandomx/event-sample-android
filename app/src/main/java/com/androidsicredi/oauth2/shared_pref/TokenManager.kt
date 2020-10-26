package com.androidsicredi.oauth2.shared_pref

import android.content.SharedPreferences
import com.androidsicredi.oauth2.model.Token

class TokenManager private constructor(private val sharedPreferences: SharedPreferences) {
    private val editor: SharedPreferences.Editor
    fun saveToken(token: Token) {
        editor.putString(SHARED_PREFERENCES_ACCESS_TOKEN, token.accessToken).commit()
        editor.putString(SHARED_PREFERENCES_REFRESH_TOKEN, token.refreshToken).commit()
    }

    val token: Token
        get() {
            val token = Token()
            token.accessToken = sharedPreferences.getString(SHARED_PREFERENCES_ACCESS_TOKEN, null)
            token.refreshToken = sharedPreferences.getString(SHARED_PREFERENCES_REFRESH_TOKEN, null)
            return token
        }

    companion object {
        const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
        private const val SHARED_PREFERENCES_ACCESS_TOKEN = "SHARED_PREFERENCES_ACCESS_TOKEN"
        private const val SHARED_PREFERENCES_REFRESH_TOKEN = "SHARED_PREFERENCES_REFRESH_TOKEN"
        private var INSTANCE: TokenManager? = null

        @JvmStatic
        @Synchronized
        fun getIntance(sharedPreferences: SharedPreferences): TokenManager? {
            if (INSTANCE == null) {
                INSTANCE = TokenManager(sharedPreferences)
            }
            return INSTANCE
        }
    }

    init {
        editor = sharedPreferences.edit()
    }
}