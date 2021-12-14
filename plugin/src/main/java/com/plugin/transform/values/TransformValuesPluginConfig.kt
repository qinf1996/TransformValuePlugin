package com.plugin.transform.values

import com.plugin.transform.values.utils.Logger
import java.io.File

open class TransformValuesPluginConfig {

    var excelFile: File? = null
    var outputFile: File? = null
    var sheetName: String = ""
    var stringNameColumn: Int = 0
    var defaultValueColumn: Int = -1
    private val mapLanguage: HashMap<Int, String> = hashMapOf()

//        mapLanguage.put(1, "b+zh+Hans")
//        mapLanguage.put(2, "en")
//        mapLanguage.put(3, "es")
//        mapLanguage.put(4, "pt")
//        mapLanguage.put(5, "fr")
//        mapLanguage.put(6, "de")
//        mapLanguage.put(7, "it")
//        mapLanguage.put(8, "ru")
//        mapLanguage.put(9, "ro")
//        mapLanguage.put(10, "bg")
//        mapLanguage.put(11, "el")
//        mapLanguage.put(12, "nl")
//        mapLanguage.put(13, "hu")
//        mapLanguage.put(14, "pl")
//        mapLanguage.put(15, "b+zh+Hant")
//        mapLanguage.put(16, "tr")
//        mapLanguage.put(17, "th")
//        mapLanguage.put(18, "sr")
//        mapLanguage.put(19, "in")
//        mapLanguage.put(20, "vi")
//        mapLanguage.put(21, "ar")
//        mapLanguage.put(22, "ja")
//        mapLanguage.put(23, "ko")
//        mapLanguage.put(24, "my")
//        mapLanguage.put(25, "pt-rBR")
//        mapLanguage.put(26, "km-rKH")
//        mapLanguage.put(27, "ms")
//        mapLanguage.put(28, "b+zh+Hant+TW")


    internal fun checkParams() {
        checkNotNull(excelFile, {
            " excelFile is null"
        })
        checkNotNull(outputFile, {
            " outputFile is null"
        })

        if (excelFile?.exists()?.not() == true) {
            throw RuntimeException(" file not found , $excelFile ")
        }

        if (isXlsFile().not() && isXlsxFile().not()) {
            throw RuntimeException(" The is not excel file , $excelFile ")
        }
    }

    internal fun buildValuesFile(index: Int): File? {
        return mapLanguage[index]?.let { languageCode ->
            StringBuilder(VALUE_FILE).apply {
                if (index != defaultValueColumn) {
                    append("-")
                    append(languageCode)
                }
                append(File.separator)
                append(STRINGS_FILE)
            }.let {
                File(outputFile, it.toString()).also {
                    Logger.i(" buildValuesFile ... index $index , languageCode $languageCode")
                }
            }
        }
    }

    fun mappingValues(column: Int, languageCode: String) {
        mapLanguage.putIfAbsent(column, languageCode)
    }

    internal fun isXlsFile() = excelFile?.absolutePath?.endsWith(XLS, true) ?: false

    internal fun isXlsxFile() = excelFile?.absolutePath?.endsWith(XLSX, true) ?: false

    override fun toString(): String {
        return "TransformValuesPluginConfig(excelFile=${excelFile?.absolutePath}, outputFile=${outputFile?.absolutePath}, sheetName='$sheetName', stringNameColumn=$stringNameColumn, defaultValueColumn=$defaultValueColumn, mapLanguage=$mapLanguage)"
    }


    companion object {
        private const val VALUE_FILE = "values"
        private const val STRINGS_FILE = "strings.xml"
        private const val XLS = "xls"
        private const val XLSX = "xlsx"
    }
}