package com.loguito.pdftool

expect class PDFTool {
    fun fillForm(information: Map<String, String>): String
}