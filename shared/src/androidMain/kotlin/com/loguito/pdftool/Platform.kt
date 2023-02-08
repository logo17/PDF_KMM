package com.loguito.pdftool

class AndroidPlatform : Platform {
    override val name: String = "Android Test1"
}

actual fun getPlatform(): Platform = AndroidPlatform()