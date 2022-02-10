package com.nubari.montra.general.util

import android.content.Context
import android.content.SharedPreferences
import com.nubari.montra.general.util.Constants.AUTH_TOKEN_PREF_KEY
import com.nubari.montra.general.util.Constants.HAS_ONBOARDED_PREF_KEY
import com.nubari.montra.general.util.Constants.MONTRA_SHARED_PREF_FILE
import com.nubari.montra.general.util.Constants.USER_ID_PREF_KEY

class Preferences(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(MONTRA_SHARED_PREF_FILE, Context.MODE_PRIVATE)


    var hasOnboarded: Boolean
        get() = preferences.getBoolean(HAS_ONBOARDED_PREF_KEY, false)
        set(value) = preferences.edit().putBoolean(HAS_ONBOARDED_PREF_KEY, value).apply()
    var authenticationToken: String
        get() = preferences.getString(AUTH_TOKEN_PREF_KEY, "") ?: ""
        set(value) = preferences.edit().putString(AUTH_TOKEN_PREF_KEY, value).apply()

    var userId: String
        get() = preferences.getString(USER_ID_PREF_KEY, "") ?: ""
        set(value) = preferences.edit().putString(USER_ID_PREF_KEY, value).apply()

    fun clearAuthenticationToken() {
        val editor = preferences.edit()
        editor.remove(AUTH_TOKEN_PREF_KEY)
        editor.apply()

    }


}