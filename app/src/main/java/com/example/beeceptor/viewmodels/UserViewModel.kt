package com.example.anilcomposeex.hiltMVVMCOMPOSE.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anilcomposeex.hiltMVVMCOMPOSE.modals.UserItem
import com.example.anilcomposeex.hiltMVVMCOMPOSE.repositiory.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {
    val userItem : StateFlow<List<UserItem>>
        get() = userRepository.userItem

    init {
        viewModelScope.launch {
            userRepository.getUserItem()
        }
    }
    override fun onCleared() {

        super.onCleared()
    }

}