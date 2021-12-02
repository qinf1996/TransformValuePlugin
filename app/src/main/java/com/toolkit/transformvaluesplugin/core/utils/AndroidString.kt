package com.toolkit.transformvaluesplugin.core.utils


data class AndroidString(val key: String, val value: String) {

    fun escapeValue() = StringEscapeUtils.escape(value)

    override fun toString(): String {
        return "<string name=\"$key\">${escapeValue()}</string>"
    }
}