package com.example.beeceptor.api

import com.example.anilcomposeex.hiltMVVMCOMPOSE.modals.UserItem
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("/users")
    suspend fun getUsers(): Response<List<UserItem>>
}