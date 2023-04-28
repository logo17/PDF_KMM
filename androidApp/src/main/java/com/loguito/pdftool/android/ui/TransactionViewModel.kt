package com.loguito.pdftool.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loguito.pdftool.android.ui.utils.SingleLiveEvent
import com.loguito.pdftool.network.ServiceLocator
import com.loguito.pdftool.network.domain.cb.UserData
import com.loguito.pdftool.network.model.User
import kotlinx.coroutines.launch

class TransactionViewModel : ViewModel(), UserData {

    private val _user = SingleLiveEvent<User>()
    val user: SingleLiveEvent<User> = _user

    private val presenter by lazy {
        ServiceLocator.getUserPresenter
    }

    fun getUserInformation(userId: String) {
        presenter.fetchUserInformation(this, userId)
    }

    override fun onUserData(user: User) {
        viewModelScope.launch {
            _user.value = user
        }
    }
}