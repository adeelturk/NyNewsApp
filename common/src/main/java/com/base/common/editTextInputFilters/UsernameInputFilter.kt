package com.base.common.editTextInputFilters

import android.content.Context
import android.text.InputFilter
import android.text.Spanned

class UsernameInputFilter(val context: Context, val error: () -> Unit) : InputFilter {
    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {

        // Only keep characters that are alphanumeric
        val builder = StringBuilder()
        for (i in start until end) {
            val c = source[i]
            if (Character.isLetterOrDigit(c)) {
                builder.append(c)
            } else {
                error()
                return builder
            }
        }
        return builder
//        // If all characters are valid, return null, otherwise only return the filtered characters
//        val allCharactersValid = builder.length == end - start
//        return if (allCharactersValid) null else builder.toString()
    }
}