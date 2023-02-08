package com.loguito.pdftool

import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSURL.Companion.URLWithString
import platform.Foundation.temporaryDirectory
import platform.PDFKit.PDFAnnotation
import platform.PDFKit.PDFDocument
import platform.PDFKit.fieldName
import platform.PDFKit.widgetStringValue

actual class PDFTool(private val fileURL: NSURL) {
    actual val generatedFilePath: String = generateDoc()

    private fun generateDoc(): String {
        val pdfDocument = PDFDocument(fileURL)
        val dataRepresentation = pdfDocument.dataRepresentation()
        dataRepresentation?.let { data ->
            for (i in 0 until pdfDocument.pageCount.toInt()) {
                pdfDocument.pageAtIndex(i.toULong())?.let { page ->
                    val annotations: List<PDFAnnotation> =
                        page.annotations as List<PDFAnnotation>
                    for (annotation in annotations) {
                        if (annotation.fieldName == "FirstName") {
                            annotation.widgetStringValue = "Filled Text Field"
                            page.addAnnotation(annotation)
                        }
                    }
                }
            }
            pdfDocument.writeToURL(fileURL)
            return fileURL.relativeString()
        }
        return ""
    }

}
