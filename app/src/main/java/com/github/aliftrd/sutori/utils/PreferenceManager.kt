package com.github.aliftrd.sutori.utils

import android.content.Context
import android.content.SharedPreferences
import com.github.aliftrd.sutori.data.auth.dto.LoginResult

class PreferenceManager(context: Context) {
    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(ConstVar.PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun setStringPreference(prefKey: String, value: String) {
        editor.putString(prefKey, value)
        editor.apply()
    }

    fun setLoginPref(userItem: LoginResult) {
        userItem.let {
            setStringPreference(ConstVar.TOKEN_KEY, it.token)
            setStringPreference(ConstVar.NAME_KEY, it.name)
        }
    }

    fun clearAllPreferences() {
        editor.remove(ConstVar.TOKEN_KEY)
        editor.remove(ConstVar.NAME_KEY)
        editor.apply()
    }


    val getToken = prefs.getString(ConstVar.TOKEN_KEY, "") ?: ""
    val name = prefs.getString(ConstVar.NAME_KEY, "") ?: ""

}