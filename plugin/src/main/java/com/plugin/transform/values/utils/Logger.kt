package com.plugin.transform.values.utils

import org.gradle.api.Project
import org.gradle.api.logging.Logger

internal object Logger {

    private var sLogger: Logger? = null

    fun make(project: Project) {
        sLogger = project.logger
    }

    fun i(message: String) {
        sLogger?.info(message)
    }

    fun w(message: String, throwable: Throwable? = null) {
        sLogger?.warn(message, throwable)
    }

    fun e(message: String, throwable: Throwable? = null) {
        sLogger?.error(message, throwable)
    }
}