package ru.reglek.checkpoint.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

interface TextFormatter {
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
}

class TextFormatterImpl @Inject constructor(
    private var context: Context
) : TextFormatter {

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}