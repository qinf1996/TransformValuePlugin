package com.plugin.transform.values.parse

import com.plugin.transform.values.TransformValuesPluginConfig
import com.plugin.transform.values.utils.AndroidString
import com.plugin.transform.values.utils.Logger
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream

internal class ExcelResourcesParser {

    internal fun parseExcelFile(config: TransformValuesPluginConfig) {

        val fis = FileInputStream(config.getExcelFile())

        val workbook: Workbook =
            takeIf { config.isXlsxFile() }?.let { XSSFWorkbook(fis) } ?: HSSFWorkbook(fis)

        val sheet = config.sheetName.takeIf { it.isNotEmpty() }?.let {
            workbook.getSheet(it)
        } ?: workbook.getSheetAt(0)

        parseCellWriteToFile(config, sheet)
    }

    private fun parseCellWriteToFile(config: TransformValuesPluginConfig, sheet: Sheet) {
        val columnCount = sheet.getRow(sheet.firstRowNum).lastCellNum
        val rowCount = sheet.lastRowNum
        Logger.i("parseSheetCell#columnCount: $columnCount rowCount: $rowCount")
        for (col in 0 until columnCount) {
            if (col == config.stringNameColumn) {
                continue
            }
            val stringList = prepareMap()
            for (r in 0..rowCount) {
                sheet.getRow(r)?.apply {
                    getCell(config.stringNameColumn)?.toString()
                        ?.takeIf { it.isNotEmpty() }
                        ?.also { name ->
                            val value = getCell(col)?.toString()?.takeIf { it.isNotEmpty() }
                                ?: getCell(config.defaultValueColumn)?.toString().orEmpty()
                            value.takeIf { it.isNotEmpty() }?.also {
                                stringList.add(AndroidString(name, value))
                            }
                        }
                }
            }
            XmlResourcesWriter.writerToXml(config.buildValuesFile(col), stringList)
            releaseMap(stringList)
        }
    }

    private fun releaseMap(list: MutableList<AndroidString>): MutableList<AndroidString> {
        synchronized(LINKED_MAP_POOL) {
            for (i in 0 until POOL_SIZE) {
                if (LINKED_MAP_POOL[i] == null) {
                    LINKED_MAP_POOL[i] = list
                    break
                }
            }
        }
        return list
    }

    private fun prepareMap(): MutableList<AndroidString> {
        synchronized(LINKED_MAP_POOL) {
            for (i in 0 until POOL_SIZE) {
                val list = LINKED_MAP_POOL[i]
                if (list != null) {
                    LINKED_MAP_POOL[i] = null
                    return list.apply { clear() }
                }
            }
        }
        return mutableListOf()
    }

    companion object {
        private const val POOL_SIZE = 4
        private val LINKED_MAP_POOL: Array<MutableList<AndroidString>?> = arrayOfNulls(POOL_SIZE)
    }
}