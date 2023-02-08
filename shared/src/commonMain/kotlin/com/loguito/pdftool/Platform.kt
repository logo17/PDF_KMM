package com.loguito.pdftool

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform