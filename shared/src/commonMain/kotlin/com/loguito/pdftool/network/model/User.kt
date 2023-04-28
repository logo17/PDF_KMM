package com.loguito.pdftool.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(var data: User)

@Serializable
data class User(var id: Int = -1, var first_name: String = "", var last_name: String = "", var avatar: String = "")