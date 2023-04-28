package com.loguito.pdftool.network.domain.cb

import com.loguito.pdftool.network.model.User

public interface UserData {
    public fun onUserData(user: User)
}