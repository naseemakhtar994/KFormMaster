package com.thejuki.kformmaster.model

import android.os.Parcel

import java.io.Serializable

/**
 * Form Picker Element
 *
 * Base class for Picker form elements
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class FormPickerElement<T : Serializable> : BaseFormElement<T> {

    /**
     * Parcelable boilerplate
     */
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
    }

    constructor(tag: Int = 0) : super(tag)

    protected constructor(`in`: Parcel) : super(`in`) {}

    companion object {
        fun createInstance(): FormPickerElement<String> {
            return FormPickerElement()
        }

        fun <T : Serializable> createGenericInstance(): FormPickerElement<T> {
            return FormPickerElement()
        }
    }
}
