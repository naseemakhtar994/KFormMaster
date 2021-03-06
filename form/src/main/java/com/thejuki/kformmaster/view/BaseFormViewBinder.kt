package com.thejuki.kformmaster.view

import android.app.Dialog
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.view.ViewGroup
import com.thejuki.kformmaster.model.BaseFormElement

/**
 * Base Form ViewBinder
 *
 * Base setup for title, error, and visibility
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
abstract class BaseFormViewBinder {

    fun baseSetup(formElement: BaseFormElement<*>, textViewTitle: AppCompatTextView?,
                  textViewError: AppCompatTextView?,
                  itemView: View) {

        // Setup title and error if present
        if (textViewTitle != null) {
            textViewTitle.text = formElement.title
        }

        if (textViewError != null) {
            setError(textViewError, formElement.error)
        }

        if (formElement.isVisible()) {
            itemView.visibility = View.VISIBLE
            itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        } else {
            itemView.visibility = View.GONE
            itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        }
    }

    fun setError(textViewError: AppCompatTextView, error: String?) {
        if (error.isNullOrEmpty()) {
            textViewError.visibility = View.GONE
            return
        }

        textViewError.text = error
        textViewError.visibility = View.VISIBLE
    }

    fun setOnClickListener(editTextValue: AppCompatEditText, itemView: View, dialog: Dialog) {
        editTextValue.isFocusable = false

        // display the dialog on click
        val listener = View.OnClickListener {
            dialog.show()
        }

        itemView.setOnClickListener(listener)
        editTextValue.setOnClickListener(listener)
    }
}