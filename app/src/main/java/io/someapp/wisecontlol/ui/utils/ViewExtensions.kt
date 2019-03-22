package io.someapp.wisecontlol.ui.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible


fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}


fun EditText.showKeyboard() {
    this.post {
        if (this.requestFocus()) {
            val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

fun TextView.asString(): String = text?.toString() ?: ""

fun View.switchVisibility() {
    showView(!isVisible)
}

fun View.showView(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
