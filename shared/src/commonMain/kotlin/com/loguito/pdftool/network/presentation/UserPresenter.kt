package com.loguito.pdftool.network.presentation

import com.loguito.pdftool.network.domain.GetUserData
import com.loguito.pdftool.network.domain.cb.UserData
import com.loguito.pdftool.network.model.User
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class UserPresenter(private val getUserData: GetUserData) {

    public fun fetchUserInformation(cb: UserData, userID: String) {
        MainScope().launch {
            getUserData.invokeGetUserInformation(
                userID = userID,
                onSuccess = { cb.onUserData(it) },
                onFailure = { cb.onUserData(User()) }
            )
        }
    }

}