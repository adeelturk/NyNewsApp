package com.base.common.customView.spinner

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.base.common.extensions.getSpinnerValue
import com.base.common.extensions.setSpinnerEntries
import com.base.common.extensions.setSpinnerInverseBindingListener
import com.base.common.extensions.setSpinnerValue


@BindingAdapter("entries")
fun Spinner.setEntries(entries: List<Any>?) {
    setSpinnerEntries(entries)
}

/**
* This method set the value to the view which is coming from viewModel/presenter/model(viewModel in our case)
 *
* */

@BindingAdapter("selectedValue")
fun Spinner.setSelectedValue(selectedValue: Any?) {
    setSpinnerValue(selectedValue)
}

/**
 * This method is listening if their is any
 * @see InverseBindingAdapter is attached with the binding this method will invoke and set the
 * listener to listen when the defined attribute is change
 *
 * The @see InverseBindingListener is the class provided from the android to listen on the views property
 * change by default the binding system will attach the "AttrChanged" at the end of the default view
 * method which is changeable
 *
 * the suffix AttrChanged is
 *
 *
 * When the listener.onChange() is invoked it will call the method annotated as @see InverseBindingAdapter
 */
@BindingAdapter("selectedValueAttrChanged")
fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
    setSpinnerInverseBindingListener(inverseBindingListener)
}


/**
 * @param attribute is checking for the value which is defined in the
 * @see BindingAdapter of the same view it will match the value of the attribute with the attribute
 * defined in the binding adapter for setting the view
 *
 * @param event this will match the value with the same name mentioned in binding adapter to set the
 * listener against it
 *
 * this method is responsible to get the value from the view
 *
 * getter that will call our viewModel/presenter/model(viewModel in our case) setAttribute
 */
@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun Spinner.getSelectedValue(): Any? {
    return getSpinnerValue()
}





