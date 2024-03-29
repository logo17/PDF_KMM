package com.loguito.pdftool

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDDocumentCatalog
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDAcroForm
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDTextField
import java.io.File
import java.io.IOException


actual class PDFTool(
    private val context: Context,
    private val docId: Int
    ) {

    actual fun fillForm(information: Map<String, String>): String {
        PDFBoxResourceLoader.init(context)
        try {
            // Load the document and get the AcroForm
            val document: PDDocument =
                PDDocument.load(context.resources.openRawResource(docId))
            val docCatalog: PDDocumentCatalog = document.documentCatalog
            val acroForm: PDAcroForm = docCatalog.acroForm

            // Fill the text fields
            information.map { entry ->
                val field: PDTextField = acroForm.getField(entry.key) as PDTextField
                field.defaultAppearance = acroForm.defaultAppearance
                field.value = entry.value
                field.isReadOnly = true
            }

            val baseDir = File(context.filesDir, "PDFs")
            if (!baseDir.exists()) baseDir.mkdir()
            val newPdfFile = File(baseDir, "FilledForm.pdf")
            document.save(newPdfFile)
            document.close()
            return FileProvider.getUriForFile(context, "com.example.fileprovider", newPdfFile).toString()
        } catch (e: IOException) {
            Log.e("PdfBox-Android-Sample", "Exception thrown while filling form fields", e)
        }
        return ""
    }
}