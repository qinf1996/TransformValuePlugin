package com.toolkit.transformvaluesplugin

import com.toolkit.transformvaluesplugin.core.utils.AndroidString
import com.toolkit.transformvaluesplugin.core.TransformValuesPluginConfig
import com.toolkit.transformvaluesplugin.core.parse.ExcelResourcesParser
import com.toolkit.transformvaluesplugin.core.parse.XmlResourcesWriter

class KotlinMain {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val excelPath = "C:\\Users\\qinf\\Desktop\\AiScore全量翻译.xlsx"
            val outputPath = "E:\\WorkSpace\\TransformValuesPlugin\\app\\src\\main\\res"
            val config = TransformValuesPluginConfig(excelPath, outputPath,defaultValueColumn = 2)
            ExcelResourcesParser().apply {
                parseExcelFile(config)
            }
//            XmlResourcesWriter().apply {
//                val list = arrayListOf<AndroidString>().apply {
//                    for (i in 0 until 10) {
//
//                        add(AndroidString("index$i", "Lig's$i"))
//                    }
//                }
//                writeXml(outputPath, list)
//            }
        }
    }
}