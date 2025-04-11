package com.bambang.pokeapi.data.local

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveLoginSession(username: String, name: String) {
        prefs.edit()
            .putBoolean("is_login", true)
            .putString("username", username)
            .putString("name", name)
            .apply()
    }

    fun isLogin(): Boolean {
        return prefs.getBoolean("is_login", false)
    }

    fun logout() {
        prefs.edit().remove("is_login").apply()
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun getUsername(): String? {
        return prefs.getString("username", null)
    }

    fun getName(): String? {
        return prefs.getString("name", null)
    }
}