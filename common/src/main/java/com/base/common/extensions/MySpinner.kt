package com.base.common.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.base.common.customView.spinner.MyCustomSpinner
import com.base.common.R


fun MyCustomSpinner.setupSpinnerAdapter(
    typeArray: Array<String>,
    context: Context,
    callback: (Int, String) -> Unit
) {

    val spinnerArrayAdapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
        context, R.layout.spinner_item, typeArray
    ) {
        override fun isEnabled(position: Int): Boolean =
            position != 0

        override fun getDropDownView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View? {
            val view = super.getDropDownView(position, convertView, parent)
            val tv: TextView = view.findViewById(R.id.accountType)
            if (position == 0) { // Set the hint text color gray
                tv.setTextColor(Color.GRAY)
            } else {
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
            return view
        }
    }
    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
    this.setAdapter(spinnerArrayAdapter)
     this.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View?,
            position: Int,
            id: Long
        ) {

            if (position < 0) {

                return
            }

            view?.run {

                try {
                    val selectedItemText =
                        parent.getItemAtPosition(position) as String
                    val tv: TextView = view.findViewById(R.id.accountType)
                    if (position == 0) { // Set the hint text color gray
                        tv.setTextColor(Color.GRAY)

                    } else {
                        tv.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.primaryTextColor
                            )
                        )

                        callback(position - 1, selectedItemText)

                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }


            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    })
}