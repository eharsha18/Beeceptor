package com.example.anilcomposeex.hiltMVVMCOMPOSE.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anilcomposeex.hiltMVVMCOMPOSE.modals.UserItem
import com.example.anilcomposeex.hiltMVVMCOMPOSE.repositiory.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {
    val userItem : StateFlow<List<UserItem>>
        get() = userRepository.userItem

    init {
        val handler = CoroutineExceptionHandler { _, throwable->
            println("Exception caught $throwable")
        }
        viewModelScope.launch(Dispatchers.IO+handler) {
            userRepository.getUserItem()
        }
    }
}