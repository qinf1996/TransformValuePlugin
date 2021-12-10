package com.plugin.transform.values

import com.plugin.transform.values.parse.ExcelResourcesParser
import com.plugin.transform.values.utils.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project

class TransformValuesPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        Logger.make(project)
        val config =
            project.extensions.create("transformValuesConfig", TransformValuesPluginConfig::class.java)
        Logger.i(" config " + config)
//        config.checkParams()
//        ExcelResourcesParser().apply {
//            parseExcelFile(config)
//        }
    }
}