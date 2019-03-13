package io.someapp.wisecontlol.data


import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

interface WisePreference {
}

class WisePreferenceImpl @Inject constructor(
    context: Context
) : WisePreference {

    private val pref = context.getSharedPreferences("пуф", Context.MODE_PRIVATE)

}

private fun SharedPreferences.putInt(key: String, value: Int) {
    this.edit().putInt(key, value).apply()
}

private fun SharedPreferences.putBoolean(key: String, value: Boolean) {
    this.edit().putBoolean(key, value).apply()
}