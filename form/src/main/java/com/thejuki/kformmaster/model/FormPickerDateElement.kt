package com.thejuki.kformmaster.model

import android.os.Parcel
import android.os.Parcelable

import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Form Picker Date Element
 *
 * Form element for AppCompatEditText (which on click opens a Date dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerDateElement : FormPickerElement<FormPickerDateElement.DateHolder> {
    class DateHolder : Serializable {

        var isEmptyDate: Boolean = false
        var dayOfMonth: Int? = null
        var month: Int? = null
        var year: Int? = null
        var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()

        constructor(dayOfMonth: Int, month: Int, year: Int) {
            this.dayOfMonth = dayOfMonth
            this.month = month
            this.year = year
        }

        constructor() {
            useCurrentDate()
        }

        constructor(date: Date?, dateFormat: DateFormat) {
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                this.year = calendar.get(Calendar.YEAR)
                this.month = calendar.get(Calendar.MONTH) + 1
                this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            } else {
                isEmptyDate = true
            }

            this.dateFormat = dateFormat
        }

        override fun toString(): String {
            return if (isEmptyDate || year == null || month == null || dayOfMonth == null) ""
            else dateFormat.format(getTime())
        }

        fun validOrCurrentDate() {
            if (year == null || month == null || dayOfMonth == null) {
                useCurrentDate()
            }
        }

        fun useCurrentDate() {
            val calendar = Calendar.getInstance()
            this.year = calendar.get(Calendar.YEAR)
            this.month = calendar.get(Calendar.MONTH) + 1
            this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            isEmptyDate = true
        }

        fun getTime(): Date? {
            if (year == null || month == null || dayOfMonth == null) {
                return null
            }
            val calendar = Calendar.getInstance()
            calendar.set(year!!, month!! - 1, dayOfMonth!!)

            return calendar.time
        }

        fun getTime(zone: TimeZone,
                    aLocale: Locale): Date? {
            if (year == null || month == null || dayOfMonth == null) {
                return null
            }
            val calendar = Calendar.getInstance(zone, aLocale)
            calendar.set(year!!, month!! - 1, dayOfMonth!!)

            return calendar.time
        }

        fun equals(another: DateHolder): Boolean {
            return another.isEmptyDate == this.isEmptyDate &&
                    another.year == this.year &&
                    another.month == this.month &&
                    another.dayOfMonth == this.dayOfMonth
        }
    }

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

    constructor(`in`: Parcel) : super(`in`) {}

    companion object {


        fun createDateInstance(): FormPickerDateElement {
            return FormPickerDateElement()
        }

        val CREATOR: Parcelable.Creator<FormPickerDateElement> = object : Parcelable.Creator<FormPickerDateElement> {
            override fun createFromParcel(source: Parcel): FormPickerDateElement {
                return FormPickerDateElement(source)
            }

            override fun newArray(size: Int): Array<FormPickerDateElement?> {
                return arrayOfNulls(size)
            }
        }
    }
}
