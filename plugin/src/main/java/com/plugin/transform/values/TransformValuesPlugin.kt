package com.plugin.transform.values

import com.plugin.transform.values.parse.ExcelResourcesParser
import com.plugin.transform.values.utils.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project

class TransformValuesPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        Logger.make(project)
        Logger.i(" apply ... ")
        val config = project.extensions.create(
            "transformValuesConfig",
            TransformValuesPluginConfig::class.java
        )

        project.tasks.create("transformValues") {
            it.group = "transform"
            it.doLast {
                Logger.i("transformValues#doLast.. , config:$config")
                config.checkParams()
                ExcelResourcesParser().apply {
                    parseExcelFile(config)
                }
            }
        }
    }
}