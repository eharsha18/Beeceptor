package com.example.anilcomposeex.hiltMVVMCOMPOSE.modals

import kotlinx.serialization.Serializable

@Serializable
data class UserItem(
    val address: String,
    val company: String,
    val country: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val photo: String,
    val state: String,
    val username: String,
    val zip: String
)