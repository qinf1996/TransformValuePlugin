package com.plugin.transform.values.utils


internal data class AndroidString(val key: String, val value: String) {

    internal fun escapeValue() = StringEscapeUtils.escape(value)

    override fun toString(): String {
        return "<string name=\"$key\">${escapeValue()}</string>"
    }
}