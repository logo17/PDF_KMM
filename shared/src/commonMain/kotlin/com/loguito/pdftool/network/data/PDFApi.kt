package com.loguito.pdftool.network.data

import com.loguito.pdftool.network.model.User
import com.loguito.pdftool.network.model.UserInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal

public const val API_URL = "https://reqres.in/api/users"

@ThreadLocal
public object PDFApi {
    private val nonStrictJson = Json {
        isLenient = true; ignoreUnknownKeys = true
    }

    private val client: HttpClient = HttpClient() {
        install(ContentNegotiation) {
            json(nonStrictJson)
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    public suspend fun fetchUserInformation(userId: String): UserInfo {
        return client.get("$API_URL/$userId").body()
    }
}