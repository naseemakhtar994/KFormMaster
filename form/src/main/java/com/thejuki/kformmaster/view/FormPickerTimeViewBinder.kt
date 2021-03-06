package com.thejuki.kformmaster.view

import android.app.TimePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerTimeElement

/**
 * Form Picker Time ViewBinder
 *
 * View Binder for [FormPickerTimeElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTimeViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormPickerTimeElement::class.java) { model, finder, _ ->
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""

        // If no value is set by the user, create a new instance of TimeHolder
        with(model.value)
        {
            if (this == null) {
                model.setValue(FormPickerTimeElement.TimeHolder())
            }
            this?.validOrCurrentTime()
        }

        val timePickerDialog = TimePickerDialog(context,
                timeDialogListener(model),
                model.value?.hourOfDay!!,
                model.value?.minute!!,
                false)

        setOnClickListener(editTextValue, itemView, timePickerDialog)
    }

    private fun timeDialogListener(model: FormPickerTimeElement): TimePickerDialog.OnTimeSetListener {
        return TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            var timeChanged = false
            with(model.value)
            {
                timeChanged = !(this?.hourOfDay == hourOfDay && this.minute == minute)
                this?.hourOfDay = hourOfDay
                this?.minute = minute
                this?.isEmptyTime = false
            }

            if (timeChanged) {
                model.setError(null) // Reset after value change
                model.valueChanged?.onValueChanged(model)
                formBuilder.onValueChanged(model)
                formBuilder.refreshView()
            }
        }
    }
}
