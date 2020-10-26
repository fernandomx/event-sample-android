package com.androidsicredi.oauth2.api

import com.androidsicredi.oauth2.shared_pref.TokenManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class CustomAuthenticator private constructor(private val tokenManager: TokenManager) : Authenticator {

    /*
    O método de autenticação é executado quando nosso token de acesso expira e queremos obter um novo token de acesso com
     nosso token de atualização.
    Caso o token de atualização tenha expirado, faremos o logout.


    */
    /*
    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        val authHeader = Base64.encodeToString("androidApp:123".toByteArray(), Base64.NO_WRAP)
        val token = tokenManager.token
        val call = instance
                ?.createService(WebServiceOAuthApi::class.java)
                ?.obterTokenconRefreshToken(
                        authHeader,
                        token.refreshToken,
                        "refresh_token"
                )
        val response1 = call?.execute()
        return if (response1!!.isSuccessful) {
            /*
            Se a resposta estiver correta, atualizamos o token de acesso.
             */
            val newToken = response1.body()
            newToken?.let { tokenManager.saveToken(it) }
            response.request().newBuilder().header("Authorization", "Bearer " + response1.body()!!.accessToken).build()
        } else {
            /*
           Se a resposta não for bem-sucedida, significa que o token de atualização expirou
            e, portanto, teremos que sair do aplicativo
            e peça ao usuário para inserir novamente suas credenciais.
              */
            null
        }
    }

     */

    companion object {
        private var INSTANCE: CustomAuthenticator? = null

        @Synchronized
        fun getInstance(tokenManager: TokenManager): CustomAuthenticator? {
            if (INSTANCE == null) {
                INSTANCE = CustomAuthenticator(tokenManager)
            }
            return INSTANCE
        }
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("Not yet implemented")
    }

}