//package com.plugin.transform.values
//
//import java.io.File
//import java.lang.RuntimeException
//
//data class TransformValuesPluginConfig(
//    val excelPath: String,
//    val outputPath: String,
//    val sheetName: String = "",
//    val stringNameColumn: Int = 0,
//    val defaultValueColumn: Int = -1,
//    val mapLanguage: HashMap<Int, String> = hashMapOf()
//) {
//    init {
//        if (isXlsFile().not() && isXlsxFile().not()) {
//            throw RuntimeException(" The is not excel file , $excelPath ")
//        }
//
//        if (outputPath.isEmpty()) {
//            throw RuntimeException(" output path is empty ")
//        }
//
////        mapLanguage.put(1, "b+zh+Hans")
////        mapLanguage.put(2, "en")
////        mapLanguage.put(3, "es")
////        mapLanguage.put(4, "pt")
////        mapLanguage.put(5, "fr")
////        mapLanguage.put(6, "de")
////        mapLanguage.put(7, "it")
////        mapLanguage.put(8, "ru")
////        mapLanguage.put(9, "ro")
////        mapLanguage.put(10, "bg")
////        mapLanguage.put(11, "el")
////        mapLanguage.put(12, "nl")
////        mapLanguage.put(13, "hu")
////        mapLanguage.put(14, "pl")
////        mapLanguage.put(15, "b+zh+Hant")
////        mapLanguage.put(16, "tr")
////        mapLanguage.put(17, "th")
////        mapLanguage.put(18, "sr")
////        mapLanguage.put(19, "in")
////        mapLanguage.put(20, "vi")
////        mapLanguage.put(21, "ar")
////        mapLanguage.put(22, "ja")
////        mapLanguage.put(23, "ko")
////        mapLanguage.put(24, "my")
////        mapLanguage.put(25, "pt-rBR")
////        mapLanguage.put(26, "km-rKH")
////        mapLanguage.put(27, "ms")
////        mapLanguage.put(28, "b+zh+Hant+TW")
//    }
//
//    private val outputPathCompat = outputPath.replace("\\", File.separator)
//
//    internal fun getExcelFile() = File(excelPath)
//
//    internal fun buildValuesFile(index: Int) = mapLanguage[index]?.let { name ->
//        if (index == defaultValueColumn) {
//            VALUE_FILE
//        } else {
//            "$VALUE_FILE-$name"
//        }.let { it + File.separator + STRINGS_FILE }.let {
//            File(outputPathCompat, it)
//        }
//    }
//
//    fun mappingValues(column: Int, languageCode: String) {
//        mapLanguage.putIfAbsent(column, languageCode)
//    }
//
//    internal fun isXlsFile() = excelPath.endsWith("xls", true)
//
//    internal fun isXlsxFile() = excelPath.endsWith("xlsx", true)
//
//    companion object {
//        private const val VALUE_FILE = "values"
//        private const val STRINGS_FILE = "strings.xml"
//    }
//}