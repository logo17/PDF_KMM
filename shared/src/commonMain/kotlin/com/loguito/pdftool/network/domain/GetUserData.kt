package com.loguito.pdftool.network.domain

import com.loguito.pdftool.network.data.PDFApi
import com.loguito.pdftool.network.model.User
import kotlinx.coroutines.coroutineScope

class GetUserData {
    public suspend fun invokeGetUserInformation(
        userID: String,
        onSuccess: (User) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val result = PDFApi.fetchUserInformation(userID)

            coroutineScope {
                onSuccess(result.data)
            }
        } catch (exception: Exception) {
            coroutineScope {
                onFailure(exception)
            }
        }
    }
}