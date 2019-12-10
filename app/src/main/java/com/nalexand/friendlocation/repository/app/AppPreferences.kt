package com.nalexand.friendlocation.repository.app

import android.content.Context
import com.nalexand.friendlocation.model.local.Token
import com.nalexand.friendlocation.model.response.TokenResponse
import com.nalexand.friendlocation.utils.AppConstants.INTERNAL_PREFS

class AppPreferences(
    private val appContext: Context
) {

    companion object {
        const val TOKEN_VALUE_PREF_KEY = "token_value_pk"
        const val TOKEN_EXPIRATION_PREF_KEY = "token_expiration_pk"
    }

    fun getToken(): Token {
        val value = getString(TOKEN_VALUE_PREF_KEY)
        val expiration = getLong(TOKEN_EXPIRATION_PREF_KEY)
        return Token(value, expiration)
    }

    fun saveToken(token: TokenResponse) {
        putString(TOKEN_VALUE_PREF_KEY, token.access_token)
    }

    fun clearToken() {
        remove(TOKEN_VALUE_PREF_KEY)
    }

    private fun getLong(key: String): Long {
        return preferences?.getLong(key, 0) ?: 0
    }

    private fun putString(key: String, value: String) {
        preferences?.edit()
            ?.putString(key, value)
            ?.apply()
    }

    private fun getString(key: String): String? {
        return preferences?.getString(key, null)
    }

    private fun remove(key: String) {
        preferences?.edit()
            ?.remove(key)
            ?.apply()
    }

    private val preferences by lazy {
        appContext.getSharedPreferences(INTERNAL_PREFS, Context.MODE_PRIVATE)
    }
}