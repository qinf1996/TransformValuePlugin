package com.plugin.transform.values.parse

import com.plugin.transform.values.utils.AndroidString
import com.plugin.transform.values.utils.Logger
import org.apache.poi.ooxml.util.DocumentHelper
import org.apache.poi.util.XMLHelper
import java.io.File
import java.io.FileOutputStream
import javax.xml.transform.ErrorListener
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

internal object XmlResourcesWriter {

    private const val TAG_RESOURCES = "resources"
    private const val TAG_STRING = "string"
    private const val ATTR_NAME = "name"

    // todo 后续增加增量
    fun writerToXml(file: File?, list: MutableList<AndroidString>) {
        if (file == null || file.absolutePath.isEmpty()) {
            Logger.e(" writerToXml file is null or path is empty  ")
            return
        }

        makeFile(file)

        val document = DocumentHelper.createDocument()
        document.xmlStandalone = true
        val root = document.createElement(TAG_RESOURCES)

        val map = list.associateBy { it.key }.toMutableMap()

        map.forEach {
            val string = document.createElement(TAG_STRING).apply {
                setAttribute(ATTR_NAME, it.key)
                textContent = it.value.escapeValue()
            }
            root.appendChild(string)
        }

        document.appendChild(root)

        val trans = XMLHelper.newTransformer()
        trans.setOutputProperty(OutputKeys.INDENT, "yes")
        trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8")

        val result = StreamResult(FileOutputStream(file))
        val source = DOMSource(document)
        trans.transform(source, result)

        trans.errorListener = object : ErrorListener {
            override fun warning(exception: TransformerException?) {
                Logger.w(exception?.message.orEmpty(), exception)
            }

            override fun error(exception: TransformerException?) {
                Logger.e(exception?.message.orEmpty(), exception)
            }

            override fun fatalError(exception: TransformerException?) {
                Logger.e(exception?.message.orEmpty(), exception)
            }
        }
    }


    private fun makeFile(f: File) {
        f.apply {
            if (parentFile?.exists()?.not() == true) {
                parentFile?.mkdirs()
            }
            if (exists().not()) {
                createNewFile()
            }
        }
    }
}