package com.base.common.editTextInputFilters

import android.content.Context
import android.text.InputFilter
import android.text.Spanned


class PlateNumberInputFilter(val context: Context, private val indexesOfLettersArray: ArrayList<Int>,
                              val error : ()->Unit ) : InputFilter {
    override fun filter(
            source: CharSequence, start: Int, end: Int,
            dest: Spanned?, dstart: Int, dend: Int,
    ): CharSequence? {

        // Only keep characters that are alphanumeric
        val builder = StringBuilder()
        for (i in start until end) {
            val c = source[i]


            if (indexesOfLettersArray.contains(dstart)) {
                indexesOfLettersArray.forEach { index ->
                    if (dstart == index) {
                        if (Character.isLetter(c)) {
                            builder.append(c)
                        } else {
                            error()
                            return builder
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                        }
                    }
                }

            } else {
                if (Character.isDigit(c)) {
                    builder.append(c)
                } else {
                    error()
                    return builder
//                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }
        }
        return builder


        // If all characters are valid, return null, otherwise only return the filtered characters
//        val allCharactersValid = builder.length == end - start
//        return if (allCharactersValid) null else builder.toString()
    }
}