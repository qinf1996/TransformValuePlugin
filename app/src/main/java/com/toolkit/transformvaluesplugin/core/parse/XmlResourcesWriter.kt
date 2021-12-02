package com.toolkit.transformvaluesplugin.core.parse

import com.toolkit.transformvaluesplugin.core.utils.AndroidString
import com.toolkit.transformvaluesplugin.core.utils.Logger
import org.apache.poi.ooxml.util.DocumentHelper
import org.apache.poi.util.XMLHelper
import org.w3c.dom.Element
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.xml.transform.ErrorListener
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object XmlResourcesWriter {

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
//        if (root.hasChildNodes()) {
//            for (i in 0 until root.childNodes.length) {
//                root.childNodes.item(i)?.also { node ->
//                    val item = node.attributes.getNamedItem(ATTR_NAME)
//                    map[item.nodeValue]?.also {
//                        item.textContent = it.escapeValue()
//                        map.remove(item.nodeValue)
//                    }
//                }
//            }
//        }

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