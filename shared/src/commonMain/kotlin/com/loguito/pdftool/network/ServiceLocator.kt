package com.loguito.pdftool.network

import com.loguito.pdftool.network.domain.GetUserData
import com.loguito.pdftool.network.presentation.UserPresenter
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
public object ServiceLocator {
    private val getUser: GetUserData = GetUserData()

    public val getUserPresenter: UserPresenter = UserPresenter(getUser)
}