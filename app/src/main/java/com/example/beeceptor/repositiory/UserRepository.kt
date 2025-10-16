package com.example.anilcomposeex.hiltMVVMCOMPOSE.repositiory

import com.example.anilcomposeex.hiltMVVMCOMPOSE.modals.UserItem
import com.example.beeceptor.api.UserApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userItem = MutableStateFlow<List<UserItem>>(emptyList())
    val userItem : StateFlow<List<UserItem>>
        get() = _userItem

    suspend fun getUserItem() {
        val result = userApi.getUsers()
        if(result.isSuccessful && result.body()!=null) {
            _userItem.emit(result.body()!!)
        }
    }
}